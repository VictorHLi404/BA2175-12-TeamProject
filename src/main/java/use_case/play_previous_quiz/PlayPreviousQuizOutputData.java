package use_case.play_previous_quiz;

import java.util.UUID;

public class PlayPreviousQuizOutputData {
    public final UUID quizId;

    public PlayPreviousQuizOutputData(UUID quizId) {
        this.quizId = quizId;
    }

    public UUID getQuizId() {
        return quizId;
    }
}
