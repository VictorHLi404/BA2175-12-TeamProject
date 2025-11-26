package use_case.view_score;

import entities.QuizResults;
import entities.User;
import persistence.FileReaderGateway;

import java.time.format.DateTimeFormatter;
import java.util.*;


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
        List<PerQuizResultData> perQuizData = new ArrayList<>();
        int totalQuestions = 0;
        int totalCorrectAnswers = 0;
        for (QuizResults quizResults : allQuizResults.values()){
            if (quizResults.getUserId().equals(userID)){
                totalQuestions += quizResults.getQuizSize();
                totalCorrectAnswers += quizResults.getScore();
                String timeStamp = quizResults.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                perQuizData.add(new PerQuizResultData(timeStamp, quizResults.getScore(), quizResults.getQuizSize()));
            }
        }
        if (perQuizData.isEmpty()){
            viewScoreOutputBoundary.prepareNoResultsView(username);
        }
        else {
            perQuizData.sort(Comparator.comparing(PerQuizResultData::getDateTime));
            double rawScore = (double) totalCorrectAnswers / totalQuestions;
            int scorePercentage = (int) (rawScore * 100);
            ViewScoreOutputData outputData = new ViewScoreOutputData(username, scorePercentage,perQuizData);
            viewScoreOutputBoundary.prepareSuccessView(outputData);
        }

    }
    public void switchToMainMenuView() {
        viewScoreOutputBoundary.switchToMainMenuView();
    }
}
