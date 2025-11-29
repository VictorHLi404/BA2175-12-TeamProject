package interface_adapter.customize_quiz;


import use_case.customize_quiz.CustomizeQuizInputBoundary;
import use_case.customize_quiz.CustomizeQuizInputData;

public class CustomizeQuizController {

    private final CustomizeQuizInputBoundary interactor;

    public CustomizeQuizController(CustomizeQuizInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void applyCustomization(String difficulty, String type, String category) {
        interactor.execute(new CustomizeQuizInputData(difficulty, type, category, false));
    }

    public void resetCustomization() {
        interactor.execute(new CustomizeQuizInputData(null, null, null, true));
    }
}
