package use_case.view_score;

import entities.Question;
import entities.Quiz;
import entities.QuizResults;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.DataStore;
import persistence.FileReaderGateway;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewScoreTest {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path QUESTIONS_FILE = DATA_DIR.resolve("questions.json");

    private DataStore writer;
    private FileReaderGateway reader;

    private List<UUID> questionsIds;
    private List<Question> questions;
    private Quiz quiz;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() throws IOException {
        writer = new JsonFileDataStore(true);
        reader = new JsonFileReader(true);

        Question q1 = new Question("multiple", "easy", "1 + 1 = ?", List.of("1", "2", "3"), "2", false, "ABC");
        Question q2 = new Question("multiple", "medium", "Capital of France?", List.of("Paris", "London", "Berlin"), "Paris", true, "ABC");
        Question q3 = new Question("boolean", "hard", "The sun is a star.", List.of("True", "False"), "True", false, "ABC");

        writer.saveQuestion(q1);
        writer.saveQuestion(q2);
        writer.saveQuestion(q3);

        questionsIds = List.of(q1.getQuestionId(), q2.getQuestionId(), q3.getQuestionId());
        quiz = new Quiz(questionsIds, true, questionsIds.size());
        writer.saveQuiz(quiz);

        questions = List.of(q1, q2, q3);

        user1 = new User("alice", "12345");
        user2 = new User("bob", "12345");
        writer.saveUser(user1);
        writer.saveUser(user2);
    }

    @Test
    void testSingleQuizResultSuccess() {
        // Alice takes quiz, all correct
        QuizResults results = new QuizResults(quiz, user1.getUserId(), List.of("2", "Paris", "True"), questions);
        writer.saveQuizResults(results);

        ViewScoreInputData inputData = new ViewScoreInputData("alice");

        ViewScoreOutputBoundary presenter = new ViewScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewScoreOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
                assertEquals(100, outputData.getScore());
                assertEquals(1, outputData.getPerQuizResultData().size());

                PerQuizResultData r = outputData.getPerQuizResultData().get(0);
                assertEquals(3, r.getTotal());
                assertEquals(3, r.getCorrect());
                assertEquals(results.getQuizResultsId(), r.getQuizResultId());
            }

            @Override public void prepareFailView(String username) { fail(); }
            @Override public void prepareNoResultsView(String username) { fail(); }
            @Override public void switchToMainMenuView() {}
        };

        new ViewScoreInteractor(reader, presenter).execute(inputData);
    }

    @Test
    void testMultipleResultsSortingAndScore() {
        QuizResults res1 = new QuizResults(quiz, user1.getUserId(), List.of("2", "Paris", "True"), questions); // 3/3
        QuizResults res2 = new QuizResults(quiz, user1.getUserId(), List.of("1", "Paris", "True"), questions); // 2/3
        writer.saveQuizResults(res1);
        writer.saveQuizResults(res2);

        ViewScoreInputData inputData = new ViewScoreInputData("alice");

        ViewScoreOutputBoundary presenter = new ViewScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewScoreOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
                assertEquals(83, outputData.getScore()); // (3+2)/(3+3) = 5/6 = 83%

                List<PerQuizResultData> list = outputData.getPerQuizResultData();
                assertEquals(2, list.size());

                // Check timestamp ordering
                LocalDateTime t1 = LocalDateTime.parse(list.get(0).getDateTime());
                LocalDateTime t2 = LocalDateTime.parse(list.get(1).getDateTime());
                assertTrue(t1.isBefore(t2) || t1.equals(t2));

                // Check correctness per quiz result
                assertEquals(3, list.get(0).getTotal());
                assertEquals(3, list.get(0).getCorrect());
                assertEquals(3, list.get(1).getTotal());
                assertEquals(2, list.get(1).getCorrect());
            }

            @Override public void prepareFailView(String username) { fail(); }
            @Override public void prepareNoResultsView(String username) { fail(); }
            @Override public void switchToMainMenuView() {}
        };

        new ViewScoreInteractor(reader, presenter).execute(inputData);
    }

    @Test
    void testNoResultsForExistingUser() {
        ViewScoreInputData inputData = new ViewScoreInputData("bob");

        ViewScoreOutputBoundary presenter = new ViewScoreOutputBoundary() {
            @Override public void prepareNoResultsView(String username) { assertEquals("bob", username); }
            @Override public void prepareSuccessView(ViewScoreOutputData outputData) { fail(); }
            @Override public void prepareFailView(String username) { fail(); }
            @Override public void switchToMainMenuView() {}
        };

        new ViewScoreInteractor(reader, presenter).execute(inputData);
    }

    @Test
    void testInvalidUsernameFails() {
        ViewScoreInputData inputData = new ViewScoreInputData("ghost");

        ViewScoreOutputBoundary presenter = new ViewScoreOutputBoundary() {
            @Override public void prepareFailView(String username) { assertEquals("ghost", username); }
            @Override public void prepareSuccessView(ViewScoreOutputData outputData) { fail(); }
            @Override public void prepareNoResultsView(String username) { fail(); }
            @Override public void switchToMainMenuView() {}
        };

        new ViewScoreInteractor(reader, presenter).execute(inputData);
    }
}
