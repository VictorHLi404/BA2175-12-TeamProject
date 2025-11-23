package use_case.view_score;

/**
 * The output boundary for the View Score Use Case.
 */
public interface ViewScoreOutputBoundary {

    /**
     * Prepares the success view for the View Score Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ViewScoreOutputData outputData);

}
