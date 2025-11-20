package entities;

import persistence.JsonFileReader;

import java.util.List;
import java.util.UUID;

public class QuizResults {
    private UUID quizResultsId;
    private List<UUID> questions;
    private List<String> answers;
    private int score;

    public QuizResults(Quiz quiz, List<String> answers) {
        this.quizResultsId = quiz.getQuizId();
        this.questions = quiz.getQuestionIds();
        this.answers = answers;
        this.score = calculateScore();
    }

    private int calculateScore() {
        JsonFileReader reader = new JsonFileReader();
        int count = 0;
        for (int i = 0; i < Math.min(questions.size(), answers.size()); i++) {
            Question question = reader.loadQuestions(questions.get(i));
            if (question.isCorrect(answers.get(i))) {
                count++;
            }
        }
        return count;
    }

    public int getScore() { return score; }
    public UUID getQuizResultsId() { return quizResultsId; }
}
