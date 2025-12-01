package use_case.play_previous_quiz;

/**
 * The output boundary for the Play Previous Quiz Use Case.
 */
import use_case.view_score.ViewScoreOutputData;

import java.util.UUID;


public interface PlayPreviousQuizOutputBoundary {

    /**
     * Prepares the success view for the View Score Use Case.
     * @param quizId the output data
     */
    void prepareSuccessView(UUID quizId);

}
