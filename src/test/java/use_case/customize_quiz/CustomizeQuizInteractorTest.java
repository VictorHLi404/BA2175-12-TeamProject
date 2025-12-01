package use_case.customize_quiz;

import entities.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.customize_quiz.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CustomizeQuizInteractor.
 */
public class CustomizeQuizInteractorTest {

    // A fake DAO that returns a fixed List<Question> — no real API calls.
    private static class FakeQuizDAO implements CustomizeQuizDataAccessInterface {

        List<Question> fakeQuestions;

        FakeQuizDAO(List<Question> fakeQuestions) {
            this.fakeQuestions = fakeQuestions;
        }

        @Override
        public List<Question> fetchQuestions(int amount, String category, String difficulty, String type)
                throws IOException {
            return fakeQuestions;
        }
    }

    private static class TestPresenter implements CustomizeQuizOutputBoundary {

        CustomizeQuizOutputData outputData = null;
        String errorMessage = null;

        @Override
        public void prepareSuccessView(CustomizeQuizOutputData outputData) {
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            this.errorMessage = error;
        }
    }

    private CustomizeQuizInteractor interactor;
    private TestPresenter presenter;
    private FakeQuizDAO fakeDAO;

    @BeforeEach
    void setUp() {
        List<Question> sampleQuestions = Arrays.asList(
                new Question(
                        "multiple",
                        "easy",
                        "Q1",
                        Arrays.asList("A", "B", "C"),
                        "A",
                        false,
                        "General Knowledge"
                ),
                new Question(
                        "boolean",
                        "hard",
                        "Q2",
                        Arrays.asList("True", "False"),
                        "True",
                        false,
                        "Science & Nature"
                )
        );



        presenter = new TestPresenter();
        fakeDAO = new FakeQuizDAO(sampleQuestions);
        interactor = new CustomizeQuizInteractor(fakeDAO, presenter);
    }

    @Test
    void testApplyCustomizationSuccess() {
        CustomizeQuizInputData input =
                new CustomizeQuizInputData("easy", "multiple", "9", false);

        interactor.execute(input);

        assertNotNull(presenter.outputData);
        assertNull(presenter.errorMessage);

        assertEquals(2, presenter.outputData.getCustomizedQuestions().size());

        assertTrue(presenter.outputData.getMessage().toLowerCase().contains("success"));

        assertTrue(presenter.outputData.isSuccess());
    }

    @Test
    void testResetToDefault() {
        CustomizeQuizInputData input =
                new CustomizeQuizInputData(null, null, null, true);

        interactor.execute(input);

        assertNotNull(presenter.outputData);
        assertNull(presenter.errorMessage);

        assertEquals(2, presenter.outputData.getCustomizedQuestions().size());

        assertTrue(presenter.outputData.getMessage().toLowerCase().contains("reset"));

        assertTrue(presenter.outputData.isSuccess());
    }

    @Test
    void testDAOThrowsIOException() {
        CustomizeQuizDataAccessInterface brokenDAO = new CustomizeQuizDataAccessInterface() {
            @Override
            public List<Question> fetchQuestions(int amount, String category, String difficulty, String type)
                    throws IOException {
                throw new IOException("API down");
            }
        };

        CustomizeQuizInteractor failingInteractor = new CustomizeQuizInteractor(brokenDAO, presenter);

        CustomizeQuizInputData input = new CustomizeQuizInputData("easy", "multiple", "9", false);

        failingInteractor.execute(input);

        assertNull(presenter.outputData);
        assertNotNull(presenter.errorMessage);

        assertTrue(presenter.errorMessage.contains("API down"));
    }
}
