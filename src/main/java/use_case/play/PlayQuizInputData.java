package use_case.play;

import java.util.List;
import java.util.UUID;

public class PlayQuizInputData {
    private final UUID quizId;
    private final int questionIndex;
    private final String selectedChoice;
    private final List<String> previousAnswers;

    public PlayQuizInputData(UUID quizId, int questionIndex, String selectedChoice, List<String> previousAnswers) {
        this.quizId = quizId;
        this.questionIndex = questionIndex;
        this.selectedChoice = selectedChoice;
        this.previousAnswers = previousAnswers;
    }

    public UUID getQuizId() { return quizId; }
    public int getQuestionIndex() { return questionIndex; }
    public String getSelectedChoice() { return selectedChoice; }
    public List<String> getPreviousAnswers() { return previousAnswers; }
}
