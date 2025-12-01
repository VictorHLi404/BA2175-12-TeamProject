package interface_adapter.play_previous_quiz;

import use_case.view_score.PerQuizResultData;

import java.util.UUID;

public class PlayPreviousQuizState {

    private UUID quizId;

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) { this.quizId = quizId; }
}
