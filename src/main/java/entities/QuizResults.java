package entities;

import java.util.List;
import java.util.UUID;

public class QuizResults {
    private UUID quizResultsId;
    private List<Question> questions;
    private List<String> answers;
    private int score;

    public QuizResults(List<Question> questions, List<String> answers) {
        this.quizResultsId = UUID.randomUUID();
        this.questions = questions;
        this.answers = answers;
        this.score = calculateScore();
    }

    private int calculateScore() {
        int count = 0;
        for (int i = 0; i < Math.min(questions.size(), answers.size()); i++) {
            if (questions.get(i).isCorrect(answers.get(i))) {
                count++;
            }
        }
        return count;
    }

    public int getScore() { return score; }
    public UUID getQuizResultsId() { return quizResultsId; }
}
