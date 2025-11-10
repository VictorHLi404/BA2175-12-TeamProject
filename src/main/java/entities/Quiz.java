package entities;

import java.util.List;

public class Quiz {
    private int id;
    private List<Question> questions;
    private boolean isCustom;
    private int length;

    public Quiz(int id, List<Question> questions, boolean isCustom, int length) {
        this.id = id;
        this.questions = questions;
        this.isCustom = isCustom;
        this.length = length;
    }

    // getters
    public int getId() { return id; }
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
