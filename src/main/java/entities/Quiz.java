package entities;

import java.util.List;
import java.util.UUID;
public class Quiz {
    private UUID quizId;
    private List<Question> questions;
    private boolean isCustom;
    private int length;

    public Quiz(int id, List<Question> questions, boolean isCustom, int length) {
        this.quizId = UUID.randomUUID();
        this.questions = questions;
        this.isCustom = isCustom;
        this.length = length;
    }

    // getters
    public UUID getQuizId() { return quizId; }
    public List<Question> getQuestions() {return questions;}
    public boolean getIsCustom() {return isCustom;}
    public int getLength() {return length;}

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

}
