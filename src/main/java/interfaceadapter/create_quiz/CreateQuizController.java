package interfaceadapter.create_quiz;

import usecase.create_quiz.CreateQuizInputBoundary;
import usecase.create_quiz.CreateQuizInputData;
import usecase.create_quiz.QuestionInputData;

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
