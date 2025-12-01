package interface_adapter.play_previous_quiz;

import interface_adapter.ViewModel;

public class PlayPreviousQuizViewModel extends ViewModel<PlayPreviousQuizState> {

    public static final String VIEW_SCORE_LABEL = "Play Previous Quiz";

    public PlayPreviousQuizViewModel() {
        super(VIEW_SCORE_LABEL);
        setState(new PlayPreviousQuizState());
    }
}
