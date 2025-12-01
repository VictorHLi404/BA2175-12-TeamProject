package interfaceadapter.play;

import java.util.List;

import entities.Question;
import entities.Quiz;
import usecase.play.PlayQuizInputBoundary;
import usecase.play.PlayQuizInputData;
import usecase.play.PlayQuizInteractor;

/**
 * Controller responsible for handling play-quiz interactions.
 */
public class PlayQuizController {

    /** The input boundary for executing quiz actions. */
    private final PlayQuizInputBoundary interactor;

    /**
     * Constructs a PlayQuizController.
     *
     * @param interactor the use case input boundary
     */
    public PlayQuizController(PlayQuizInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles answering a question.
     *
     * @param questionIndex   index of the question
     * @param selectedChoice  user's chosen answer
     * @param previousAnswers list of previously selected answers
     */
    public void execute(int questionIndex, String selectedChoice, List<String> previousAnswers) {
        final PlayQuizInputData inputData =
                new PlayQuizInputData(questionIndex, selectedChoice, previousAnswers);
        interactor.execute(inputData);
    }

    /**
     * Starts a customized quiz.
     *
     * @param questions the list of questions
     * @param quiz      the quiz entity
     */
    public void startCustomizedQuiz(List<Question> questions, Quiz quiz) {
        if (questions != null && !questions.isEmpty()
                && interactor instanceof PlayQuizInteractor) {
            ((PlayQuizInteractor) interactor).startCustomizedQuiz(questions, quiz);
        }
    }

    /**
     * Loads the next question in the quiz.
     */
    public void nextQuestion() {
        interactor.loadNextQuestion();
    }
}
