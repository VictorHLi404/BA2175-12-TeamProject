package use_case.compare_score;

public interface CompareScoreOutputBoundary {

    void prepareSuccessView(CompareScoreOutputData compareScoreOutputData);

    void prepareFailView(String errorMessage);

    void switchToUserScoreView();
}
