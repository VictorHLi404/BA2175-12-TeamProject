package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Quiz {
    private UUID quizId;
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

    public void addQuestionId(UUID questionId) {
        questionIds.add(questionId);
        length = questionIds.size();
    }

    public void removeQuestion(Question question) {
        questionIds.remove(question.getQuestionId());
        length = questionIds.size();
    }

}
