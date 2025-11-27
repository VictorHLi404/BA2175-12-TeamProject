package interface_adapter;

import use_case.CreateQuizInputBoundary;
import use_case.CreateQuizInputData;
import use_case.QuestionInputData;

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


}
