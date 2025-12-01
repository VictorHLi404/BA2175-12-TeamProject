package interface_adapter.create_quiz;

import use_case.create_quiz.CreateQuizInputBoundary;
import use_case.create_quiz.CreateQuizInputData;
import use_case.create_quiz.QuestionInputData;

import java.util.List;

public class CreateQuizController {

    private final CreateQuizInputBoundary createQuizInteractor;

    public CreateQuizController (CreateQuizInputBoundary createQuizInteractor) {
        this.createQuizInteractor = createQuizInteractor;
    }

    public void execute(String quizName, List<QuestionInputData> questions) {
        CreateQuizInputData createQuizInputData = new CreateQuizInputData(quizName, questions);
        createQuizInteractor.execute(createQuizInputData);
    }

    public void switchToUserScoreView() {
        createQuizInteractor.switchToUserScoreView();
    }


}
