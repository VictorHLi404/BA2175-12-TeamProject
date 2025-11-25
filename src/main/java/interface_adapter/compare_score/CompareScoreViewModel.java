package interface_adapter.compare_score;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class CompareScoreViewModel extends ViewModel<CompareScoreState> {

    public CompareScoreViewModel() {
        super("compare score");
        setState(new CompareScoreState());
    }
}
