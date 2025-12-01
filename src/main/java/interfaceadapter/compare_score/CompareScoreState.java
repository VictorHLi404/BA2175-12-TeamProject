package interfaceadapter.compare_score;

import entities.QuizResults;

import java.util.ArrayList;
import java.util.List;

public class CompareScoreState {

    private String quizName = "";
    private String compareScoreError;
    private List<QuizResults> quizResults = new ArrayList<>();
    private List<List<String>> normalizedQuizResults = new ArrayList<>();

    public String getQuizName() {
        return quizName;
    }

    public String getQuizNameOrDefault() {
        if (quizName == null || quizName.isEmpty()) {
            return "No Quiz Name Found";
        }
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

    public List<List<String>> getNormalizedQuizResults() {
        return normalizedQuizResults;
    }

    public void  setNormalizedQuizResults(List<List<String>> normalizedQuizResults) {
        this.normalizedQuizResults = normalizedQuizResults;
    }
}
