package usecase.view_score;

import java.util.List;

/**
 * Output Data for the View Score Use Case.
 */
public class ViewScoreOutputData {

    private final String username;
    private final int score;
    private final List<PerQuizResultData> perQuizResultData;

    public ViewScoreOutputData(String username, int score, List<PerQuizResultData> perQuizResultData) {
        this.username = username;
        this.score = score;
        this.perQuizResultData = perQuizResultData;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public List<PerQuizResultData> getPerQuizResultData() {
        return perQuizResultData;
    }
}
