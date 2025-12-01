package interfaceadapter.customize_quiz;

import usecase.customize_quiz.CustomizeQuizOutputBoundary;
import usecase.customize_quiz.CustomizeQuizOutputData;

public class CustomizeQuizPresenter implements CustomizeQuizOutputBoundary {

    private final CustomizeQuizViewModel viewModel;

    public CustomizeQuizPresenter(CustomizeQuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CustomizeQuizOutputData outputData) {

        viewModel.setQuestions(outputData.getCustomizedQuestions());
        viewModel.setMessage(outputData.getMessage());
        viewModel.setSuccess(true);

        if (outputData.getMessage().toLowerCase().contains("reset")) {
            viewModel.setResetRequested(true);
        } else {
            viewModel.setResetRequested(false);
        }

        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.setMessage(error);
        viewModel.setSuccess(false);
        viewModel.setResetRequested(false);
        viewModel.firePropertyChanged();
    }
}
