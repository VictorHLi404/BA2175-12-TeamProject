package entities;

import persistence.JsonFileReader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuizResults {
    private UUID quizResultsId;
    private UUID quizId;
    private UUID userId;
    private List<UUID> questions;
    private List<String> answers;
    private int score;
    private final String timestamp;
    private int quizLength;
    private List<Question> questionObjects;


    public QuizResults(Quiz quiz, UUID userId, List<String> answers, List<Question> questionObjects) {
        this.quizResultsId = UUID.randomUUID();
        this.quizId = quiz.getQuizId();
        this.userId = userId;
        this.questions = quiz.getQuestionIds();
        this.answers = answers;
        this.questionObjects = questionObjects;
        this.score = calculateScore();
        this.timestamp = LocalDateTime.now().toString();
        this.quizLength = questions.size();
    }

    private int calculateScore() {
        int count = 0;
        for (int i = 0; i < Math.min(questionObjects.size(), answers.size()); i++) {
            if (questionObjects.get(i).isCorrect(answers.get(i))) {
                count++;
            }
        }
        return count;
    }

    public int getQuizSize() {return questions.size();}

    public int getScore() { return score; }

    public UUID getQuizResultsId() { return quizResultsId; }

    public String getTimestamp() { return timestamp; }

    public int getQuizLength() { return quizLength; }

    public UUID getQuizId() { return quizId; }

    public UUID getUserId() { return userId; }
}
