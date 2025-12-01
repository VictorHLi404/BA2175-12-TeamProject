package usecase.compare_score;

import java.util.UUID;

public interface CompareScoreInputBoundary {
    void execute(CompareScoreInputData compareScoreInputData);

    void switchToCompareScoreView(UUID quizResultsId);

    void switchToUserScoreView();
}
