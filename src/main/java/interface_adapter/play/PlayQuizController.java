package interface_adapter.play;

import use_case.play.PlayQuizInputBoundary;
import use_case.play.PlayQuizInputData;

import java.util.List;
import java.util.UUID;

public class PlayQuizController {

    private final PlayQuizInputBoundary interactor;

    public PlayQuizController(PlayQuizInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(UUID quizId, int questionIndex, String selectedChoice, List<String> previousAnswers) {
        PlayQuizInputData inputData = new PlayQuizInputData(quizId, questionIndex, selectedChoice, previousAnswers);
        interactor.execute(inputData);
    }
}
