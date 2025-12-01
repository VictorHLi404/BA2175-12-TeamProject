package interfaceadapter.compare_score;

import interfaceadapter.session.SessionManager;
import usecase.compare_score.CompareScoreInputBoundary;
import usecase.compare_score.CompareScoreInputData;

import java.util.UUID;

public class CompareScoreController {
    private final CompareScoreInputBoundary compareScoreInteractor;
    private final SessionManager sessionManager;

    public CompareScoreController(CompareScoreInputBoundary compareScoreInteractor,
                                  SessionManager sessionManager) {
        this.compareScoreInteractor = compareScoreInteractor;
        this.sessionManager = sessionManager;
    }

    public void execute(UUID quizId) {
        final CompareScoreInputData inputData = new CompareScoreInputData(quizId,
                sessionManager.getCurrentUser().getUserId());
        compareScoreInteractor.execute(inputData);
    }

    public void executeQuizResultsId(UUID quizResultsId) {
        compareScoreInteractor.switchToCompareScoreView(quizResultsId);
    }

    public void switchToUserScoreView() {
        compareScoreInteractor.switchToUserScoreView();
    }
}
