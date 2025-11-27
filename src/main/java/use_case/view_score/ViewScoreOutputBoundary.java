package use_case.view_score;

import java.util.UUID;

/**
 * The output boundary for the View Score Use Case.
 */
public interface ViewScoreOutputBoundary {

    /**
     * Prepares the success view for the View Score Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ViewScoreOutputData outputData);

    void prepareNoResultsView(String username);

    void prepareFailView(String error);

    void switchToMainMenuView();
}
