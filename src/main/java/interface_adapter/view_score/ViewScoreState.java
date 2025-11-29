package interface_adapter.view_score;

import use_case.view_score.PerQuizResultData;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the View Score View Model.
 */

public class ViewScoreState {

    private String username = "";
    private String viewMessage = "";
    private int score;
    private List<PerQuizResultData> perQuizData = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public String getViewMessage() {
        return viewMessage;
    }

    public int getScore() {
        return score;
    }

    public List<PerQuizResultData> getPerQuizResultData() { return perQuizData; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setViewMessage(String viewMessage) {
        this.viewMessage = viewMessage;
    }

    public void setPerQuizData(List<PerQuizResultData> perQuizData) {this.perQuizData = perQuizData;}

}
