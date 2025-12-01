package usecase.compare_score;

import entities.QuizResults;

import java.util.List;

public class CompareScoreOutputData {

    private final String quizName;
    private final List<QuizResults> quizResults;
    private final List<List<String>> normalizedQuizResults;

    public CompareScoreOutputData(String quizName, List<QuizResults> quizResults, List<List<String>> normalizedQuizResults) {
        this.quizName = quizName;
        this.quizResults = quizResults;
        this.normalizedQuizResults = normalizedQuizResults;
    }

    public String getQuizName() {
        return quizName;
    }

    public List<QuizResults> getQuizResults() {
        return quizResults;
    }

    public List<List<String>> getNormalizedQuizResults() {
        return normalizedQuizResults;
    }

}
