package interface_adapter.play;

import use_case.play.PlayQuizOutputBoundary;
import use_case.play.PlayQuizOutputData;

public class PlayQuizPresenter implements PlayQuizOutputBoundary {

    private final PlayQuizViewModel playQuizViewModel;

    public PlayQuizPresenter(PlayQuizViewModel playQuizViewModel) {
        this.playQuizViewModel = playQuizViewModel;
    }

    private void updateViewModel(PlayQuizOutputData outputData) {
        PlayQuizState state = playQuizViewModel.getState();
        state.setQuestionText(outputData.getNextQuestionText());
        state.setChoices(outputData.getNextChoices());
        state.setCumulativeScore(outputData.getCumulativeScore());
        state.setFinished(outputData.isFinished());
        state.setQuestionFormat(outputData.getQuestionFormat());
        state.setLastAnswerCorrect(outputData.isCorrect());
        state.setErrorMessage(null);
        playQuizViewModel.firePropertyChange();
    }

    @Override
    public void switchToMultipleChoiceView(PlayQuizOutputData outputData) {
        updateViewModel(outputData);
    }

    @Override
    public void switchToTrueFalseView(PlayQuizOutputData outputData) {
        updateViewModel(outputData);
    }

    @Override
    public void switchToCorrectAnswerView(PlayQuizOutputData outputData) {
        updateViewModel(outputData);
    }

    @Override
    public void switchToIncorrectAnswerView(PlayQuizOutputData outputData) {
        updateViewModel(outputData);
    }

    @Override
    public void switchToQuizOverView(PlayQuizOutputData outputData) {
        updateViewModel(outputData);
    }

    @Override
    public void presentError(String message) {
        PlayQuizState state = playQuizViewModel.getState();
        state.setErrorMessage(message);
        playQuizViewModel.firePropertyChange();
    }
}
