package use_case.customize_quiz;
import entities.Question;
import java.io.IOException;
import java.util.List;

public class CustomizeQuizInteractor implements CustomizeQuizInputBoundary {

    private final CustomizeQuizDataAccessInterface quizDataAccess;
    private final CustomizeQuizOutputBoundary presenter;

    public CustomizeQuizInteractor(CustomizeQuizDataAccessInterface quizDataAccess,
                                   CustomizeQuizOutputBoundary presenter) {
        this.quizDataAccess = quizDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CustomizeQuizInputData inputData) {
        try {
            if (inputData.isResetToDefault()) {
                presenter.prepareSuccessView(
                        new CustomizeQuizOutputData(
                                quizDataAccess.fetchQuestions(5, null, null, null),
                                "Customization reset to default.",
                                true
                        )
                );
                return;
            }

            List<Question> questions = quizDataAccess.fetchQuestions(
                    5,
                    inputData.getCategory(),
                    inputData.getDifficulty(),
                    inputData.getType()
            );

            CustomizeQuizOutputData outputData = new CustomizeQuizOutputData(
                    questions,
                    "Customization applied successfully.",
                    true
            );
            presenter.prepareSuccessView(outputData);

        } catch (IOException e) {
            presenter.prepareFailView("Failed to fetch customized questions: " + e.getMessage());
        }
    }
}
