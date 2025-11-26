package entities;

import persistence.JsonFileReader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuizResults {
    private UUID quizResultsId;
    private UUID quizId;
    private UUID userId;
    private List<UUID> questions;
    private List<String> answers;
    private int score;
    private final LocalDateTime timestamp;


    public QuizResults(Quiz quiz, UUID userId, List<String> answers) {
        this.quizResultsId = UUID.randomUUID();
        this.quizId = quiz.getQuizId();
        this.userId = userId;
        this.questions = quiz.getQuestionIds();
        this.answers = answers;
        this.score = calculateScore();
        this.timestamp = LocalDateTime.now();
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

    public UUID getUserId() { return userId; }

    public int getQuizSize() {return questions.size();}

    public int getScore() { return score; }

    public UUID getQuizResultsId() { return quizResultsId; }

    public LocalDateTime getTimestamp() { return timestamp; }
}
