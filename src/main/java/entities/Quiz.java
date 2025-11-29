package entities;

import persistence.JsonFileDataStore;
import persistence.JsonFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Quiz {
    private UUID quizId;
    private String quizName;
    private List<UUID> questionIds;
    private boolean isCustom;
    private int length;

    public Quiz(List<UUID> questions, boolean isCustom, int length) {
        this.quizId = UUID.randomUUID();
        this.questionIds = new ArrayList<>(questions);
        this.isCustom = isCustom;
        this.length = this.questionIds.size();
    }

    // getters
    public UUID getQuizId() { return quizId; }
    public List<UUID> getQuestionIds() { return questionIds; }
    public boolean getIsCustom() {return isCustom;}
    public int getLength() {return length;}

    public void addQuestionById(UUID questionId) {
        questionIds.add(questionId);
        length = questionIds.size();
    }

    public void addQuestion(Question question) {
        questionIds.add(question.getQuestionId());
        length = questionIds.size();
    }

    public void removeQuestionById(UUID questionId) {
        questionIds.remove(questionId);
        length = questionIds.size();
    }
    public void removeQuestion(Question question) {
        questionIds.remove(question.getQuestionId());
        length = questionIds.size();
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizName() {
        return quizName;
    }

}
