package use_case.view_score;

import entities.QuizResults;
import entities.User;
import persistence.FileReaderGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The View Score Interactor.
 */
public class ViewScoreInteractor implements ViewScoreInputBoundary
{
    private final FileReaderGateway userDataReadObject;
    private final ViewScoreOutputBoundary viewScoreOutputBoundary;


    public ViewScoreInteractor(FileReaderGateway userDataReadObject, ViewScoreOutputBoundary viewScoreOutputBoundary)
    {
        this.userDataReadObject = userDataReadObject;
        this.viewScoreOutputBoundary = viewScoreOutputBoundary;
    }

    @Override
    public void execute(ViewScoreInputData viewScoreInputData) {


        String username = viewScoreInputData.getUsername();
        User user = userDataReadObject.loadUser(username);

        if (user == null) {

            viewScoreOutputBoundary.prepareFailView(username);
            return;
        }

        UUID userID = user.getUserId();
        Map<UUID, QuizResults> allQuizResults = userDataReadObject.loadAllQuizResults();
        List<UUID> userQuizResult = new ArrayList<>();
        int totalQuestions = 0;
        int totalCorrectAnswers = 0;
        for (QuizResults quizResults : allQuizResults.values()){
            if (quizResults.getUserId().equals(userID)){
                totalQuestions += quizResults.getQuizSize();
                totalCorrectAnswers += quizResults.getScore();
            }
        }
        if (totalQuestions == 0){
            viewScoreOutputBoundary.prepareNoResultsView(username);
        }
        else {
            double rawScore = (double) totalCorrectAnswers / totalQuestions;
            int scorePercentage = (int) (rawScore * 100);
            ViewScoreOutputData outputData = new ViewScoreOutputData(username, scorePercentage);
            viewScoreOutputBoundary.prepareSuccessView(outputData);
        }

    }
    public void switchToMainMenuView() {
        viewScoreOutputBoundary.switchToMainMenuView();
    }
}
