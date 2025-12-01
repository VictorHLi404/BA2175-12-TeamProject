package interfaceadapter.play;

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
    private int cumulativeScore;
    private int currentIndex;
    private boolean finished;
    private Boolean lastAnswerCorrect;
    private String questionFormat;
    private String category;
    private String errorMessage;
    private PlayQuizViewModel.PlayQuizMode mode = PlayQuizViewModel.PlayQuizMode.START;

    /**
     * Registers a {@link PropertyChangeListener} to receive property change events
     * from this object.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Removes a previously registered {@link PropertyChangeListener} so it no longer
     * receives property change events from this object.
     *
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return correctAnswer;
    }

    public int getCumulativeScore() {
        return cumulativeScore;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean isFinished() {
        return finished;
    }

    public Boolean isLastAnswerCorrect() {
        return lastAnswerCorrect;
    }

    public String getQuestionFormat() {
        return questionFormat;
    }

    public String getCategory() {
        return category;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public void setAnswer(String answer) {
        this.correctAnswer = answer;
    }

    public void setCumulativeScore(int cumulativeScore) {
        this.cumulativeScore = cumulativeScore;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setLastAnswerCorrect(Boolean lastAnswerCorrect) {
        this.lastAnswerCorrect = lastAnswerCorrect;
    }

    public void setQuestionFormat(String questionFormat) {
        this.questionFormat = questionFormat;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PlayQuizViewModel.PlayQuizMode getMode() {
        return mode;
    }

    public void setMode(PlayQuizViewModel.PlayQuizMode mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "PlayQuizState{"
                + "questionText='" + questionText + '\''
                + ", choices=" + choices
                + ", cumulativeScore=" + cumulativeScore
                + ", finished=" + finished
                + ", lastAnswerCorrect=" + lastAnswerCorrect
                + ", questionFormat='" + questionFormat + '\''
                + ", errorMessage='" + errorMessage + '\''
                + '}';
    }
}
