package interface_adapter.play;

import java.util.List;

/**
 * State for the Play Quiz View.
 */
public class PlayQuizState {

    private String questionText = "";
    private List<String> choices;
    private int cumulativeScore = 0;
    private boolean finished = false;
    private boolean lastAnswerCorrect;
    private String questionFormat; // "multiple choice" or "true/false"
    private String errorMessage;

    public String getQuestionText() { return questionText; }
    public List<String> getChoices() { return choices; }
    public int getCumulativeScore() { return cumulativeScore; }
    public boolean isFinished() { return finished; }
    public boolean isLastAnswerCorrect() { return lastAnswerCorrect; }
    public String getQuestionFormat() { return questionFormat; }
    public String getErrorMessage() { return errorMessage; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setChoices(List<String> choices) { this.choices = choices; }
    public void setCumulativeScore(int cumulativeScore) { this.cumulativeScore = cumulativeScore; }
    public void setFinished(boolean finished) { this.finished = finished; }
    public void setLastAnswerCorrect(boolean lastAnswerCorrect) { this.lastAnswerCorrect = lastAnswerCorrect; }
    public void setQuestionFormat(String questionFormat) { this.questionFormat = questionFormat; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public String toString() {
        return "PlayQuizState{" +
                "questionText='" + questionText + '\'' +
                ", choices=" + choices +
                ", cumulativeScore=" + cumulativeScore +
                ", finished=" + finished +
                ", lastAnswerCorrect=" + lastAnswerCorrect +
                ", questionFormat='" + questionFormat + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
