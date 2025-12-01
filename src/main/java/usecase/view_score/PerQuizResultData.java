package usecase.view_score;

import java.util.UUID;

public class PerQuizResultData {
    private final String quizName;
    private final String dateTime;
    private final int correct;
    private final int total;
    private final UUID quizResultId;

    public PerQuizResultData(String dateTime, int correct, int total, UUID quizResultId, String quizName) {
        this.quizName = quizName;
        this.dateTime = dateTime;
        this.correct = correct;
        this.total = total;
        this.quizResultId = quizResultId;
    }

    public String getDateTime() { return dateTime; }
    public int getCorrect() { return correct; }
    public int getTotal() { return total; }
    public UUID getQuizResultId() {
        return quizResultId;
    }
    public String getQuizName() {
        return quizName;
    }
}
