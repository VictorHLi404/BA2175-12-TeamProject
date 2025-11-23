package interface_adapter.view_score;

/**
 * The state for the View Score View Model.
 */

public class ViewScoreState {

    private String username = "";
    private String viewMessage = "";
    private int score;

    public String getUsername() {
        return username;
    }

    public String getViewMessage() {
        return viewMessage;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setViewMessage(String viewMessage) {
        this.viewMessage = viewMessage;
    }

}
