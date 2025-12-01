package use_case.play_previous_quiz;

import use_case.view_score.ViewScoreInputData;

import java.util.UUID;

/**
 * Input Boundary for actions which are related to Play Previous Quiz.
 */

public interface PlayPreviousQuizInputBoundary {
    /**
     * Executes the Play Previous Quiz use case.
     *
     * @param quizId the input data
     */
    void execute(UUID quizId);

}
