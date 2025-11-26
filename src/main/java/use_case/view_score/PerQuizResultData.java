package use_case.view_score;

public class PerQuizResultData{
    private final String dateTime;
    private final int correct;
    private final int total;

    public PerQuizResultData(String dateTime, int correct, int total){
        this.dateTime = dateTime;
        this.correct = correct;
        this.total = total;
    }

    public String getDateTime() { return dateTime; }
    public int getCorrect() { return correct; }
    public int getTotal() { return total; }
}
