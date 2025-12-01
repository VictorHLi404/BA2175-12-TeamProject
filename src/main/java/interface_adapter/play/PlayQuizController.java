package interface_adapter.play;

import entities.Question;
import entities.Quiz;
import use_case.play.PlayQuizInputBoundary;
import use_case.play.PlayQuizInputData;
import use_case.play.PlayQuizInteractor;

import java.util.List;
import java.util.UUID;

public class PlayQuizController {

    private final PlayQuizInputBoundary interactor;

    public PlayQuizController(PlayQuizInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(int questionIndex, String selectedChoice, List<String> previousAnswers) {
        PlayQuizInputData inputData = new PlayQuizInputData(questionIndex, selectedChoice, previousAnswers);
        interactor.execute(inputData);
    }

    public void startCustomizedQuiz(List<Question> questions, Quiz quiz) {
        if (questions == null || questions.isEmpty()) return;

        if (interactor instanceof PlayQuizInteractor) {
            ((PlayQuizInteractor) interactor).startCustomizedQuiz(questions, quiz);
        }
    }

    public void nextQuestion() {
        interactor.loadNextQuestion();
    }

}

