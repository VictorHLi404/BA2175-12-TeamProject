package interfaceadapter.compare_score;

import interfaceadapter.ViewModel;

public class CompareScoreViewModel extends ViewModel<CompareScoreState> {

    public CompareScoreViewModel() {
        super("compare score");
        setState(new CompareScoreState());
    }
}
