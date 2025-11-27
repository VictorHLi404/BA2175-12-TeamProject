package interface_adapter.compare_score;

import interface_adapter.session.SessionManager;
import use_case.compare_score.CompareScoreInputBoundary;
import use_case.compare_score.CompareScoreInputData;

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
}
