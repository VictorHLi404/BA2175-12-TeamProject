package interfaceadapter.view_score;

import interfaceadapter.ViewModel;

public class ViewScoreViewModel extends ViewModel<ViewScoreState> {

    public static final String VIEW_SCORE_LABEL = "view Score";

    public ViewScoreViewModel() {
        super(VIEW_SCORE_LABEL);
        setState(new ViewScoreState());
    }
}
