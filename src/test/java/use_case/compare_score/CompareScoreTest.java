package use_case.compare_score;

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
import usecase.compare_score.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompareScoreTest {
    private static final Path DATA_DIR = Path.of("data");
    private static final Path QUESTIONS_FILE = DATA_DIR.resolve("questions.json");

    private List<UUID> questionsIds;
    private List<Question> questions;
    private Quiz quiz;
    private User user1;
    private User user2;
    private User user3;
    private DataStore writer;
    private FileReaderGateway reader;

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

        questionsIds = new ArrayList<>();
        questionsIds.add(q1.getQuestionId());
        questionsIds.add(q2.getQuestionId());
        questionsIds.add(q3.getQuestionId());

        questions = List.of(q1, q2, q3);
        quiz = new Quiz(questionsIds, true, questionsIds.size());

        writer.saveQuiz(quiz);

        user1 = new User("test user 1", "12345");
        user2 = new User("test user 2", "12345");
        user3 = new User("test user 3", "12345");

        writer.saveUser(user1);
        writer.saveUser(user2);
        writer.saveUser(user3);
    }

    @Test
    void testSingleQuizResultRetrieval() {
        List<String> answers = List.of("2", "Paris", "True");
        QuizResults quizResults = new QuizResults(quiz, user1.getUserId(), answers, questions);
        writer.saveQuizResults(quizResults);

        CompareScoreInputData inputData = new CompareScoreInputData(quiz.getQuizId(), user1.getUserId());

        CompareScoreOutputBoundary successPresenter = new CompareScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(CompareScoreOutputData compareScoreOutputData) {
                assertEquals(1, compareScoreOutputData.getQuizResults().size());
                QuizResults quizResult =  compareScoreOutputData.getQuizResults().get(0);
                assertEquals(user1.getUserId(), quizResult.getUserId());
                assertEquals(quiz.getQuizId(), quizResult.getQuizId());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToUserScoreView() {
                fail("Use case failure is unexpected.");
            }
        };
        CompareScoreInputBoundary interactor = new CompareScoreInteractor(reader, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void testMultipleQuizResultRetrievalWithSorting() {
        List<String> user1Answers = List.of("2", "Paris", "True");
        QuizResults user1QuizResults = new QuizResults(quiz, user1.getUserId(), user1Answers, questions);
        List<String> user2Answers = List.of("1", "Paris", "True");
        QuizResults user2QuizResults = new QuizResults(quiz, user2.getUserId(), user2Answers, questions);
        List<String> user3Answers = List.of("1", "Berlin", "True");
        QuizResults user3QuizResults = new QuizResults(quiz, user3.getUserId(), user3Answers, questions);
        writer.saveQuizResults(user1QuizResults);
        writer.saveQuizResults(user2QuizResults);
        writer.saveQuizResults(user3QuizResults);

        CompareScoreInputData inputData = new CompareScoreInputData(quiz.getQuizId(), user1.getUserId());

        CompareScoreOutputBoundary successPresenter = new CompareScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(CompareScoreOutputData compareScoreOutputData) {
                assertEquals(3, compareScoreOutputData.getQuizResults().size());
                QuizResults user1QuizResult =  compareScoreOutputData.getQuizResults().get(0);
                assertEquals(3, user1QuizResult.getScore());
                assertEquals(user1.getUserId(), user1QuizResult.getUserId());
                QuizResults user2QuizResult =  compareScoreOutputData.getQuizResults().get(1);
                assertEquals(user2.getUserId(), user2QuizResult.getUserId());
                assertEquals(2, user2QuizResult.getScore());
                QuizResults user3QuizResult =  compareScoreOutputData.getQuizResults().get(2);
                assertEquals(user3.getUserId(), user3QuizResult.getUserId());
                assertEquals(1, user3QuizResult.getScore());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToUserScoreView() {
                fail("Use case failure is unexpected.");
            }
        };

        CompareScoreInputBoundary interactor = new CompareScoreInteractor(reader, successPresenter);
        interactor.execute(inputData);

    }

    @Test
    void testQuizRetrievalFailure() {
        CompareScoreInputData inputData = new CompareScoreInputData(quiz.getQuizId(), UUID.randomUUID());

        CompareScoreOutputBoundary failurePresenter = new  CompareScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(CompareScoreOutputData compareScoreOutputData) {
                fail("Use case failure is unexpected.");
            }
            @Override
            public void prepareFailView(String errorMessage) {
                assertNotNull(errorMessage);
            }
            @Override
            public void switchToUserScoreView() {
                fail("Use case failure is unexpected.");
            }
        };

        CompareScoreInputBoundary interactor = new CompareScoreInteractor(reader,failurePresenter);
        interactor.execute(inputData);
    }
}
