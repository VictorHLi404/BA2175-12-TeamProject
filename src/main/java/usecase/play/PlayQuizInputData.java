package usecase.play;

import java.util.List;

public class PlayQuizInputData {
    private final int questionIndex;
    private final String selectedChoice;
    private final List<String> previousAnswers;

    public PlayQuizInputData(int questionIndex, String selectedChoice, List<String> previousAnswers) {
        this.questionIndex = questionIndex;
        this.selectedChoice = selectedChoice;
        this.previousAnswers = previousAnswers;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public String getSelectedChoice() {
        return selectedChoice;
    }

    public List<String> getPreviousAnswers() {
        return previousAnswers;
    }
}
