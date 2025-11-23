package use_case.view_score;
/**
 * Output Data for the View Score Use Case.
 */
public class ViewScoreOutputData {

    private final String username;
    private final int score;

    public ViewScoreOutputData(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
