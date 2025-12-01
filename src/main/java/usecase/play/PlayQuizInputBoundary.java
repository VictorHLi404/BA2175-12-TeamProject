package usecase.play;

import java.util.List;

import entities.Question;

/**
 * Input boundary for the play-quiz use case.
 * Defines the operations the controller can invoke on the interactor.
 */
public interface PlayQuizInputBoundary {

    /**
     * Processes a user's answer submission for the current question.
     *
     * @param inputData data containing the question index, selected answer,
     *                  and previously submitted answers
     */
    void execute(PlayQuizInputData inputData);

    /**
     * Supplies the interactor with the full list of questions that will be used
     * during the quiz session.
     *
     * @param questions the list of questions to load
     */
    void setQuestions(List<Question> questions);

    /**
     * Requests that the interactor advance to the next question
     * and trigger the appropriate presenter updates.
     */
    void loadNextQuestion();
}
