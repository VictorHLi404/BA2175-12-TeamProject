package use_case.compare_score;

public interface CompareScoreInputBoundary {
    void execute(CompareScoreInputData compareScoreInputData);

    void switchToUserScoreView();
}
