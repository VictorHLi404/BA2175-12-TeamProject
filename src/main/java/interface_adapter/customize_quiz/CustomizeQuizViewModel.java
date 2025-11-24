package interface_adapter.customize_quiz;

import entities.Question;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class CustomizeQuizViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private List<Question> questions;
    private String message;
    private boolean success;

    private boolean resetRequested;

    public void setQuestions(List<Question> qs) {
        this.questions = qs;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean s) {
        this.success = s;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isResetRequested() {
        return resetRequested;
    }

    public void setResetRequested(boolean resetRequested) {
        this.resetRequested = resetRequested;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("customizeQuiz", null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
