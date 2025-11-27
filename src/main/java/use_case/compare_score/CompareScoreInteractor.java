package use_case.compare_score;

import entities.Quiz;
import entities.QuizResults;
import entities.User;
import interface_adapter.session.SessionManager;
import persistence.DataStore;
import persistence.FileReaderGateway;

import java.util.*;

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

        List<List<String>> normalizedQuizResults = new ArrayList<>();
        for  (QuizResults results : matchedQuizResults) {
            UUID userId = results.getUserId();
            User user = fileReaderGateway.loadUser(userId);
            int score = results.getScore();
            int size  = results.getQuizSize();
            double percentage = (double) score / size * 100;
            String percentageString = String.format("%.2f%%", percentage);
            List<String> normalizedQuizResult = new ArrayList<>(Arrays.asList(user.getUsername(), percentageString));
            normalizedQuizResults.add(normalizedQuizResult);
        }

        Quiz quiz = fileReaderGateway.loadQuiz(quizId);

        final CompareScoreOutputData compareScoreOutputData = new CompareScoreOutputData(quiz.getQuizName(),
                matchedQuizResults,
                normalizedQuizResults);

        compareScorePresenter.prepareSuccessView(compareScoreOutputData);
    }

    @Override
    public void switchToCompareScoreView(UUID quizResultsId) {
        QuizResults quizresults = fileReaderGateway.loadQuizResults(quizResultsId);
        CompareScoreInputData compareScoreInputData = new CompareScoreInputData(quizresults.getQuizId(), UUID.randomUUID());
        execute(compareScoreInputData);
    }

    @Override
    public void switchToUserScoreView() {
        compareScorePresenter.switchToUserScoreView();
    }
}
