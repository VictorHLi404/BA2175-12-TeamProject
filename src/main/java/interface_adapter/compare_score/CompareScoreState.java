package interface_adapter.compare_score;

import entities.QuizResults;

import java.util.ArrayList;
import java.util.List;

public class CompareScoreState {

    private String quizName = "";
    private String compareScoreError;
    private List<QuizResults> quizResults = new ArrayList<>();

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getCompareScoreError() {
        return compareScoreError;
    }

    public void setCompareScoreError(String compareScoreError) {
        this.compareScoreError = compareScoreError;
    }

    public List<QuizResults> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(List<QuizResults> quizResults) {
        this.quizResults = quizResults;
    }
}
