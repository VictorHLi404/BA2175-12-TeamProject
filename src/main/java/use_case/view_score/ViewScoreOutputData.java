package use_case.view_score;
/**
 * Output Data for the View Score Use Case.
 */
public class ViewScoreOutputData {

    private final String username;
    private final int score;

    public ViewScoreOutputData(String username) {
        this.username = username;
        this.score = getScore();
    }

    public String getUsername() {
        return username;
    }
    public int getScore() {
        ViewScore score =  new ViewScore();
        return score.ViewAverageScore();
    }
}
