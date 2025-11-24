package interface_adapter.play;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Play Quiz View.
 */
public class PlayQuizViewModel extends ViewModel<PlayQuizState> {

    public static final String TITLE_LABEL = "Play Quiz";

    public PlayQuizViewModel() {
        super("play quiz");
        setState(new PlayQuizState());
    }
}
