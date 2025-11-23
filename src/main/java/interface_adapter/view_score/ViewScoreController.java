package interface_adapter.view_score;

import use_case.view_score.ViewScoreInputBoundary;
import use_case.view_score.ViewScoreInputData;

public class ViewScoreController {

    private final ViewScoreInputBoundary viewScoreInteractor;
            ;

    public ViewScoreController(ViewScoreInputBoundary viewScoreInteractor) {
        this.viewScoreInteractor = viewScoreInteractor;
    }

    /**
     * Executes the View Score Use Case.
     * @param username the username of the user logging in
     */
    public void execute(String username) {
        final ViewScoreInputData viewScoreInputData = new ViewScoreInputData(username);
        viewScoreInteractor.execute(viewScoreInputData);
    }
    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToMainMenuView() {
        viewScoreInteractor.switchToMainMenuView();
    }
}
