package interfaceadapter.create_quiz;

import interfaceadapter.ViewManagerModel;
import usecase.create_quiz.CreateQuizOutputBoundary;
import usecase.create_quiz.CreateQuizOutputData;

public class CreateQuizPresenter implements CreateQuizOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final CreateQuizViewModel createQuizViewModel;

    public CreateQuizPresenter(ViewManagerModel viewManagerModel,
                               CreateQuizViewModel createQuizViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.createQuizViewModel = createQuizViewModel;

    }

    @Override
    public void prepareSuccessView(CreateQuizOutputData outputData) {

        // Store that the quiz was successfully saved
        createQuizViewModel.setQuizSaved(true);

        createQuizViewModel.setQuizName(outputData.getQuizName());
        createQuizViewModel.setMessage("Quiz created!");

        createQuizViewModel.firePropertyChange();

        // Resets the default screen after user has successfully created the quiz
        createQuizViewModel.setState(new CreateQuizState());

        // Redirecting to the Main Menu page after successfully creating a quiz
        this.viewManagerModel.setState("Main Menu");
        this.viewManagerModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String errorMessage) {

        // Store that the quiz wasn't saved
        createQuizViewModel.setQuizSaved(false);
        createQuizViewModel.setMessage(errorMessage);

        createQuizViewModel.firePropertyChange();

    }

    @Override
    public void switchToUserScoreView() {
        // TODO: Add transition work to go back to score view
        createQuizViewModel.setState(new CreateQuizState());
        viewManagerModel.setState("view Score");
        viewManagerModel.firePropertyChange();
    }

}
