package use_case.view_score;

import java.util.UUID;

public class PerQuizResultData{
    private final String dateTime;
    private final int correct;
    private final int total;
    private final UUID quizResultId;

    public PerQuizResultData(String dateTime, int correct, int total, UUID quizResultId){
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
}
