package use_case.create_quiz;

import entities.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizInteractorTest {

    private testDAO DAO;

    private CreateQuizOutputBoundary successPresenter;
    private CreateQuizOutputBoundary failurePresenter;

    @BeforeEach
    void setUp() {
        DAO = new testDAO();

        // Creating a fake presenter
        successPresenter = new CreateQuizOutputBoundary() {

            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                Assertions.assertNotNull(outputData);   // Checks that the interactor created a real CreateQuizOutputData object
                Assertions.assertNotNull(outputData.getQuizName());
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail(error);
            }

            public void switchToUserScoreView() {

            }

        };

        failurePresenter = new CreateQuizOutputBoundary() {

            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                Assertions.fail("Presenter failed!");
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.assertNotNull(error);
            }

            public void switchToUserScoreView() {}

        };

    }

    @Test
    void testCreateQuizSuccessfully() {

        List<QuestionInputData> questionList = List.of(
                new QuestionInputData("General Knowledge", "Medium", "True/False",
                        "Is Los Angeles the capital city of California?", List.of(), "True"
                ),
                new QuestionInputData("Sports", "Hard", "Multiple Choice",
                        "When did the Spurs win their last NBA championship?", List.of("2014", "2023", "2005", "2007"), "2014")
        );

        CreateQuizInputData inputData = new CreateQuizInputData("myQuiz", questionList);

        CreateQuizInteractor interactor = new CreateQuizInteractor(DAO, successPresenter);
        interactor.execute(inputData);

        Assertions.assertEquals(1, DAO.quizzes.size());     // check that only 1 quiz was stored
        Quiz myQuiz = DAO.quizzes.get(0);
        Assertions.assertEquals(2, myQuiz.getLength());     // check that there were only 2 questions in the saved quiz

    }

    @Test
    void testQuizNameAlreadyExistsFails() {

        // Manually adding a quiz called "duplicated_quiz" in the DAO
        DAO.quizNames.add("duplicated_quiz");

        CreateQuizInputData inputData = new CreateQuizInputData(
                "duplicated_quiz",
                List.of(new QuestionInputData("Sports", "Hard", "Multiple Choice",
                        "When did the Spurs win their last NBA championship?", List.of("2014", "2023", "2005", "2007"), "2014"))
        );

        // Since the quiz name is duplicated, we're expecting the interactor to run prepareFailView in the failurePresenter
        CreateQuizInteractor interactor = new CreateQuizInteractor(DAO, failurePresenter);
        interactor.execute(inputData);

    }

    @Test
    void testCreateEmptyQuizFails() {

        CreateQuizInputData inputData = new CreateQuizInputData("my_quiz", new ArrayList<>());

        // Expecting the interactor to present a failView since the quiz is empty
        CreateQuizInteractor interactor = new CreateQuizInteractor(DAO, failurePresenter);
        interactor.execute(inputData);

    }

}

