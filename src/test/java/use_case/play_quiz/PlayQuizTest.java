package use_case.play_quiz;

import entities.Question;
import entities.Quiz;
import entities.QuizResults;
import entities.User;
import interface_adapter.session.SessionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.DataStore;
import persistence.FileReaderGateway;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;
import use_case.play.PlayQuizInputData;
import use_case.play.PlayQuizInteractor;
import use_case.play.PlayQuizOutputBoundary;
import use_case.play.PlayQuizOutputData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayQuizTest {
    private static final Path DATA_DIR = Path.of("data");
    private static final Path QUESTIONS_FILE = DATA_DIR.resolve("questions.json");

    private List<UUID> questionsIds;
    private List<Question> questions;
    private Quiz quiz;
    private User user;
    private DataStore writer;
    private FileReaderGateway reader;
    private SessionManager session;

    private FakePresenter presenter;
    private PlayQuizInteractor interactor;

    // Fake Presenter
    private static class FakePresenter implements PlayQuizOutputBoundary {
        String lastMethodCalled = "";
        PlayQuizOutputData lastOutput;
        Question lastQuestion;

        @Override
        public void presentQuestion(Question question, int questionIndex, int answeredCount) {
            lastMethodCalled = "presentQuestion";
            lastQuestion = question;
        }

        @Override
        public void switchToMultipleChoiceView(PlayQuizOutputData outputData) {
            lastMethodCalled = "switchToMultipleChoiceView";
            lastOutput = outputData;
        }

        @Override
        public void switchToTrueFalseView(PlayQuizOutputData outputData) {
            lastMethodCalled = "switchToTrueFalseView";
            lastOutput = outputData;
        }

        @Override
        public void switchToCorrectAnswerView(PlayQuizOutputData outputData) {
            lastMethodCalled = "switchToCorrectAnswerView";
            lastOutput = outputData;
        }

        @Override
        public void switchToIncorrectAnswerView(PlayQuizOutputData outputData) {
            lastMethodCalled = "switchToIncorrectAnswerView";
            lastOutput = outputData;
        }

        @Override
        public void switchToQuizOverView(PlayQuizOutputData outputData) {
            lastMethodCalled = "switchToQuizOverView";
            lastOutput = outputData;
        }

        @Override
        public void presentError(String message) {
            lastMethodCalled = "presentError";
        }
    }

    class FailingDataStore implements DataStore {

        @Override
        public Map<String, Integer> getCategoryToIdMapping() {
            return Map.of();
        }

        @Override
        public Map<Integer, String> getIdToCategoryMapping() {
            return Map.of();
        }

        // Implement other DataStore methods as no-ops
        @Override public void saveQuiz(Quiz quiz) {}
        @Override public void saveUser(User user) {}

        @Override
        public User loadUser(String username) {
            return null;
        }

        @Override public void saveQuestion(Question question) {}

        @Override
        public void saveQuizResults(QuizResults quizResults) {
            throw new RuntimeException("Cannot save results");
        }

    }

    @BeforeEach
    void setUp() throws IOException {

        writer = new JsonFileDataStore();
        reader = new JsonFileReader();
        Files.createDirectories(DATA_DIR);
        Files.deleteIfExists(QUESTIONS_FILE);

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
        List<UUID> qids = List.of(q1.getQuestionId(), q2.getQuestionId(), q3.getQuestionId());
        quiz = new Quiz(qids, true, qids.size());
        writer.saveQuiz(quiz);

        user = new User("tester", "pw");
        writer.saveUser(user);

        session = new SessionManager();
        session.setCurrentUser(user);

        presenter = new FakePresenter();
        interactor = new PlayQuizInteractor(presenter, session, writer, reader);

        interactor.setQuestions(questions);
    }

    @Test
    void testAnswerEvaluation() {
        // Correct answer for first question (MC)
        interactor.execute(new PlayQuizInputData(0, questions.get(0).getCorrectChoice(), List.of()));
        Assertions.assertEquals("switchToCorrectAnswerView", presenter.lastMethodCalled);
        Assertions.assertTrue(presenter.lastOutput.isCorrect());
        Assertions.assertEquals(1, presenter.lastOutput.getCumulativeScore());

        // Incorrect answer for second question (MC)
        interactor.execute(new PlayQuizInputData(1, "wrong answer", List.of()));
        Assertions.assertEquals("switchToIncorrectAnswerView", presenter.lastMethodCalled);
        Assertions.assertFalse(presenter.lastOutput.isCorrect());

        // Correct answer for third question (TF)
        Question q3 = questions.get(2);
        interactor.execute(new PlayQuizInputData(2, q3.getCorrectChoice(), List.of()));
        Assertions.assertEquals("switchToCorrectAnswerView", presenter.lastMethodCalled);
        Assertions.assertTrue(presenter.lastOutput.isCorrect());
    }

    @Test
    void testQuizFinished() {
        // answer all 3
        interactor.execute(new PlayQuizInputData(0, questions.get(0).getCorrectChoice(), List.of()));
        interactor.execute(new PlayQuizInputData(1, questions.get(1).getCorrectChoice(), List.of()));
        interactor.execute(new PlayQuizInputData(2, questions.get(2).getCorrectChoice(), List.of()));

        // trigger end
        interactor.loadNextQuestion();

        Assertions.assertEquals("switchToQuizOverView", presenter.lastMethodCalled);
        Assertions.assertTrue(presenter.lastOutput.isFinished());
    }

    @Test
    void testEmptyQuestionList() {
        PlayQuizInteractor emptyQuizInteractor =
                new PlayQuizInteractor(presenter, session, writer, reader);

        emptyQuizInteractor.setQuestions(new ArrayList<>());

        emptyQuizInteractor.loadNextQuestion();

        Assertions.assertEquals("presentError", presenter.lastMethodCalled);
    }

    @Test
    void testSaveQuizResultsThrows() {
        // Use the failing DataStore
        FailingDataStore failingStore = new FailingDataStore();
        interactor = new PlayQuizInteractor(presenter, session, failingStore, reader);

        // Set questions AND current quiz so saveQuizResults will be called
        interactor.startCustomizedQuiz(questions, quiz);

        // Answer all questions
        for (Question q : questions) {
            interactor.execute(new PlayQuizInputData(0, q.getCorrectChoice(), new ArrayList<>()));
        }

        // Trigger quiz over, which will attempt to save results and throw
        interactor.loadNextQuestion();

        // The presenter should have received an error due to the failing DataStore
        Assertions.assertEquals("presentError", presenter.lastMethodCalled);
    }
}