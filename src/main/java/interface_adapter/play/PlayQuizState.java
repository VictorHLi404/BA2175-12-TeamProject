package interface_adapter.play;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * State for the Play Quiz View.
 */
public class PlayQuizState {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String questionText = "";
    private String correctAnswer = "";
    private List<String> choices;
    private int cumulativeScore = 0;
    private int currentIndex = 0;
    private boolean finished = false;
    private Boolean lastAnswerCorrect = null;
    private String questionFormat; // "multiple" or "boolean"
    private String category;
    private String errorMessage;

    // Add listener support
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public String getQuestionText() { return questionText; }
    public List<String> getChoices() { return choices; }
    public String getAnswer(){ return correctAnswer; }
    public int getCumulativeScore() { return cumulativeScore; }
    public int getCurrentIndex() { return currentIndex; }
    public boolean isFinished() { return finished; }
    public Boolean isLastAnswerCorrect() { return lastAnswerCorrect; }
    public String getQuestionFormat() { return questionFormat; }
    public String getCategory() { return category; }
    public String getErrorMessage() { return errorMessage; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setChoices(List<String> choices) { this.choices = choices; }
    public void setAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public void setCumulativeScore(int cumulativeScore) { this.cumulativeScore = cumulativeScore; }
    public void setCurrentIndex(int currentIndex) { this.currentIndex = currentIndex; }
    public void setFinished(boolean finished) { this.finished = finished; }
    public void setLastAnswerCorrect(Boolean lastAnswerCorrect) { this.lastAnswerCorrect = lastAnswerCorrect; }
    public void setQuestionFormat(String questionFormat) { this.questionFormat = questionFormat; }
    public void setCategory(String category) { this.category = category; }
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
