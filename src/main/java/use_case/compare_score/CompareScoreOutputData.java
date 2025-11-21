package use_case.compare_score;

import entities.QuizResults;

import java.util.List;

public class CompareScoreOutputData {

    private final List<QuizResults> quizResults;

    public CompareScoreOutputData(List<QuizResults> quizResults) {
        this.quizResults = quizResults;
    }

    public List<QuizResults> getQuizResults() {
        return quizResults;
    }
}
