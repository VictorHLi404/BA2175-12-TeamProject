package use_case.compare_score;

import entities.QuizResults;
import interface_adapter.session.SessionManager;
import persistence.DataStore;
import persistence.FileReaderGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CompareScoreInteractor implements CompareScoreInputBoundary{

    private final FileReaderGateway fileReaderGateway;
    private final CompareScoreOutputBoundary compareScorePresenter;

    public CompareScoreInteractor(FileReaderGateway fileReaderGateway,
                                  CompareScoreOutputBoundary compareScorePresenter) {
        this.fileReaderGateway = fileReaderGateway;
        this.compareScorePresenter = compareScorePresenter;
    }
    @Override
    public void execute(CompareScoreInputData compareScoreInputData) {
        UUID quizId = compareScoreInputData.getQuizId();
        Map<UUID, QuizResults> allQuizResults = fileReaderGateway.loadAllQuizResults();
        List<QuizResults> matchedQuizResults = new ArrayList<>();
        for  (Map.Entry<UUID, QuizResults> entry : allQuizResults.entrySet()) {
            QuizResults results = entry.getValue();
            if (results.getQuizId().equals(quizId)) {
                matchedQuizResults.add(results);
            }
        }
        if (matchedQuizResults.isEmpty()) {
            compareScorePresenter.prepareFailView("Could not find any matching quiz results to this one");
            return;
        }
        matchedQuizResults.sort(
                (results1, results2) -> Integer.compare(results2.getScore(), results1.getScore())
        );
        final CompareScoreOutputData compareScoreOutputData = new CompareScoreOutputData(matchedQuizResults);
        compareScorePresenter.prepareSuccessView(compareScoreOutputData);
    }

    @Override
    public void switchToUserScoreView() {
        compareScorePresenter.switchToUserScoreView();
    }
}
