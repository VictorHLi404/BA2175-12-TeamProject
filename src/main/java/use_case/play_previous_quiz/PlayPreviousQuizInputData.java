package use_case.play_previous_quiz;

import java.util.UUID;

/**
 * The Input Data for the Play Previous Quiz Use Case.
 */
public class PlayPreviousQuizInputData {

    private final UUID quizId;


    public PlayPreviousQuizInputData(UUID quizId) {
        this.quizId = quizId;

    }

    UUID getQuizId() {
        return quizId;
    }
}
