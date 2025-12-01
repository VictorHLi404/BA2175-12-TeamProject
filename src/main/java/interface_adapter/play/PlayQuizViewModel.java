package interface_adapter.play;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Play Quiz View.
 */
public class PlayQuizViewModel extends ViewModel<PlayQuizState> {

    public static final String TITLE_LABEL = "playQuiz";

    public PlayQuizViewModel() {
        super("playQuiz");
        setState(new PlayQuizState());
    }
    public enum PlayQuizMode {
        START, MULTIPLE_CHOICE, TRUE_FALSE, CORRECT, INCORRECT, QUIZ_OVER
    }

    private PlayQuizMode mode;
    public PlayQuizMode getMode() { return mode; }
    public void setMode(PlayQuizMode mode) {
        this.mode = mode;
        firePropertyChange(); // notify the view
    }
}
