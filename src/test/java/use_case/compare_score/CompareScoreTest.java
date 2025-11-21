package use_case.compare_score;

import entities.Question;
import entities.Quiz;
import entities.QuizResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.DataStore;
import persistence.FileReaderGateway;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompareScoreTest {
    private static final Path DATA_DIR = Path.of("data");
    private static final Path QUESTIONS_FILE = DATA_DIR.resolve("questions.json");

    private List<UUID> questionsIds;
    private Quiz quiz;
    private DataStore writer;
    private FileReaderGateway reader;

    @BeforeEach
    void setUp() throws IOException {

        writer = new JsonFileDataStore();
        reader = new JsonFileReader();
        Files.createDirectories(DATA_DIR);
        Files.deleteIfExists(QUESTIONS_FILE);

        Question q1 = new Question("multiple", "easy", "1 + 1 = ?", List.of("1", "2", "3"), "2", false);
        Question q2 = new Question("multiple", "medium", "Capital of France?", List.of("Paris", "London", "Berlin"), "Paris", true);
        Question q3 = new Question("boolean", "hard", "The sun is a star.", List.of("True", "False"), "True", false);

        writer.saveQuestion(q1);
        writer.saveQuestion(q2);
        writer.saveQuestion(q3);

        questionsIds = new ArrayList<>();
        questionsIds.add(q1.getQuestionId());
        questionsIds.add(q2.getQuestionId());
        questionsIds.add(q3.getQuestionId());
        quiz = new Quiz(questionsIds, true, questionsIds.size());

        writer.saveQuiz(quiz);
    }

    @Test
    void testSingleQuizResultRetrieval() {
        List<String> answers = List.of("2", "Paris", "True");
        UUID userId = UUID.randomUUID();
        QuizResults quizResults = new QuizResults(quiz, userId, answers);
        writer.saveQuizResults(quizResults);

        CompareScoreInputData inputData = new CompareScoreInputData(quiz.getQuizId(), userId);

        CompareScoreOutputBoundary successPresenter = new CompareScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(CompareScoreOutputData compareScoreOutputData) {
                assertEquals(1, compareScoreOutputData.getQuizResults().size());
                QuizResults quizResult =  compareScoreOutputData.getQuizResults().get(0);
                assertEquals(userId, quizResult.getUserId());
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
        UUID user1 = UUID.randomUUID();
        List<String> user1Answers = List.of("2", "Paris", "True");
        QuizResults user1QuizResults = new QuizResults(quiz, user1, user1Answers);
        UUID user2 = UUID.randomUUID();
        List<String> user2Answers = List.of("1", "Paris", "True");
        QuizResults user2QuizResults = new QuizResults(quiz, user2, user2Answers);
        UUID user3 = UUID.randomUUID();
        List<String> user3Answers = List.of("1", "Berlin", "True");
        QuizResults user3QuizResults = new QuizResults(quiz, user3, user3Answers);
        writer.saveQuizResults(user1QuizResults);
        writer.saveQuizResults(user2QuizResults);
        writer.saveQuizResults(user3QuizResults);

        CompareScoreInputData inputData = new CompareScoreInputData(quiz.getQuizId(), user1);

        CompareScoreOutputBoundary successPresenter = new CompareScoreOutputBoundary() {
            @Override
            public void prepareSuccessView(CompareScoreOutputData compareScoreOutputData) {
                assertEquals(3, compareScoreOutputData.getQuizResults().size());
                QuizResults user1QuizResult =  compareScoreOutputData.getQuizResults().get(0);
                assertEquals(3, user1QuizResult.getScore());
                assertEquals(user1, user1QuizResult.getUserId());
                QuizResults user2QuizResult =  compareScoreOutputData.getQuizResults().get(1);
                assertEquals(user2, user2QuizResult.getUserId());
                assertEquals(2, user2QuizResult.getScore());
                QuizResults user3QuizResult =  compareScoreOutputData.getQuizResults().get(2);
                assertEquals(user3, user3QuizResult.getUserId());
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
