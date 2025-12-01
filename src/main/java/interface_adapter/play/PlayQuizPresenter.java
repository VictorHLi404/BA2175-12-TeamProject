package interface_adapter.play;

import entities.Question;
import interface_adapter.ViewManagerModel;
import use_case.play.PlayQuizOutputBoundary;
import use_case.play.PlayQuizOutputData;

public class PlayQuizPresenter implements PlayQuizOutputBoundary {

    private final PlayQuizViewModel playQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public PlayQuizPresenter(PlayQuizViewModel playQuizViewModel, ViewManagerModel viewManagerModel) {
        this.playQuizViewModel = playQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    private void updateViewModel(PlayQuizOutputData outputData, boolean preserveLastAnswer) {
        PlayQuizState state = playQuizViewModel.getState();
        state.setQuestionText(outputData.getNextQuestionText());
        state.setChoices(outputData.getNextChoices());
        state.setCumulativeScore(outputData.getCumulativeScore());
        state.setQuestionFormat(outputData.getQuestionFormat());
        // Only update lastAnswerCorrect if this is a result of an answer submission
        if (!preserveLastAnswer) {
            state.setLastAnswerCorrect(outputData.isCorrect());
        }
        state.setCurrentIndex(state.getCurrentIndex());
        state.setCategory(outputData.getCategory());
        state.setAnswer(outputData.getCorrectAnswer());
        state.setFinished(outputData.isFinished());
        state.setErrorMessage(null);
        playQuizViewModel.firePropertyChange();
    }


    @Override
    public void presentQuestion(Question question, int questionIndex, int answeredCount) {

    }

    @Override
    public void switchToMultipleChoiceView(PlayQuizOutputData outputData) {
        playQuizViewModel.getState().setMode(PlayQuizViewModel.PlayQuizMode.MULTIPLE_CHOICE);
        updateViewModel(outputData, true);
    }

    @Override
    public void switchToTrueFalseView(PlayQuizOutputData outputData) {
        playQuizViewModel.getState().setMode(PlayQuizViewModel.PlayQuizMode.TRUE_FALSE);
        updateViewModel(outputData, true);
    }

    @Override
    public void switchToCorrectAnswerView(PlayQuizOutputData outputData) {
        playQuizViewModel.getState().setMode(PlayQuizViewModel.PlayQuizMode.CORRECT);
        updateViewModel(outputData, false);
    }

    @Override
    public void switchToIncorrectAnswerView(PlayQuizOutputData outputData) {
        playQuizViewModel.getState().setMode(PlayQuizViewModel.PlayQuizMode.INCORRECT);
        updateViewModel(outputData, false);
    }

    @Override
    public void switchToQuizOverView(PlayQuizOutputData outputData) {
        playQuizViewModel.getState().setMode(PlayQuizViewModel.PlayQuizMode.QUIZ_OVER);
        updateViewModel(outputData, true);
    }

    @Override
    public void presentError(String message) {
        PlayQuizState state = playQuizViewModel.getState();
        state.setErrorMessage(message);
        playQuizViewModel.firePropertyChange();
    }
}
