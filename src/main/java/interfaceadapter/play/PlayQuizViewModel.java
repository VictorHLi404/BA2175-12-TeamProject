package interfaceadapter.play;

import interfaceadapter.ViewModel;

/**
 * ViewModel for the Play Quiz View.
 */
public class PlayQuizViewModel extends ViewModel<PlayQuizState> {

    public static final String TITLE_LABEL = "playQuiz";
    private PlayQuizMode mode;

    public PlayQuizViewModel() {
        super("playQuiz");
        setState(new PlayQuizState());
    }

    public enum PlayQuizMode {
        START, MULTIPLE_CHOICE, TRUE_FALSE, CORRECT, INCORRECT, QUIZ_OVER
    }

    public PlayQuizMode getMode() {
        return mode;
    }

    /**
     * Updates the current play-quiz mode and notifies listeners of the change.
     *
     * @param mode the new mode to set
     */
    public void setMode(PlayQuizMode mode) {
        this.mode = mode;
        firePropertyChange();
    }
}
