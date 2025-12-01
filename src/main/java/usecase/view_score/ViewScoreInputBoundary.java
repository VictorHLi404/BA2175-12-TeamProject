package usecase.view_score;


/**
 * Input Boundary for actions which are related to View Score.
 */

public interface ViewScoreInputBoundary {
    /**
     * Executes the signup use case.
     *
     * @param viewScoreInputData the input data
     */
    void execute(ViewScoreInputData viewScoreInputData);

    public void switchToMainMenuView();
}
