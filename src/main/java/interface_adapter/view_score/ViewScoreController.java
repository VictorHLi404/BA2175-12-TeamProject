package interface_adapter.view_score;

import use_case.view_score.PerQuizResultData;
import use_case.view_score.ViewScoreInputBoundary;
import use_case.view_score.ViewScoreInputData;

import java.util.UUID;

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
     * Executes the "switch to Main Menue" Use Case.
     */
    public void switchToMainMenuView() {
        viewScoreInteractor.switchToMainMenuView();
    }

    public void switchToCompareView(UUID quizResultsId) {
        viewScoreInteractor.switchToCompareView(quizResultsId); // Pass to interactor
    }

}
