package use_case.compare_score;

import java.util.UUID;

public class CompareScoreInputData {

    private final UUID quizId;
    private final UUID userId;

    public CompareScoreInputData(UUID quizId, UUID userId) {
        this.quizId = quizId;
        this.userId = userId;
    }
    public UUID getQuizId() {
        return quizId;
    }

    public UUID getUserId() {
        return userId;
    }

}
