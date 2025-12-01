package usecase.compare_score;

import java.util.UUID;

public class CompareScoreInputData {

    private final UUID quizId;
    private final UUID quizResultsId;
    private final UUID userId;

    public CompareScoreInputData(UUID quizId, UUID userId) {
        this.quizId = quizId;
        this.userId = userId;
        this.quizResultsId = null;
    }
    
    public CompareScoreInputData(UUID quizResultsId) {
        this.quizId = null;
        this.userId = null;
        this.quizResultsId = quizResultsId;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getQuizResultsId() {
        return quizResultsId;
    }

}
