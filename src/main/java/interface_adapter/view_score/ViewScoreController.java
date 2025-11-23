package interface_adapter.view_score;

import use_case.view_score.ViewScoreInputBoundary;
import use_case.view_score.ViewScoreInputData;

public class ViewScoreController {

    private final ViewScoreInputBoundary viewScoreInputBoundary;
            ;

    public ViewScoreController(ViewScoreInputBoundary viewScoreInteractor) {
        this.viewScoreInputBoundary = viewScoreInteractor;
    }

    /**
     * Executes the View Score Use Case.
     * @param username the username of the user logging in
     */
    public void execute(String username) {
        final ViewScoreInputData viewScoreInputData = new ViewScoreInputData(username);
        viewScoreInputBoundary.execute(viewScoreInputData);
    }
}
