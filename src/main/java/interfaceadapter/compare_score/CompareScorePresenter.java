package interfaceadapter.compare_score;

import interfaceadapter.ViewManagerModel;
import usecase.compare_score.CompareScoreOutputBoundary;
import usecase.compare_score.CompareScoreOutputData;

public class CompareScorePresenter implements CompareScoreOutputBoundary {

    //TODO: Link to other presenter displays when they are finished
    private final ViewManagerModel viewManagerModel;
    private final CompareScoreViewModel compareScoreViewModel;

    public CompareScorePresenter(ViewManagerModel viewManagerModel,
                                 CompareScoreViewModel compareScoreViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.compareScoreViewModel = compareScoreViewModel;
    }
    @Override
    public void prepareSuccessView(CompareScoreOutputData compareScoreOutputData) {
        viewManagerModel.setState(compareScoreViewModel.getViewName());
        viewManagerModel.firePropertyChange();
        CompareScoreState compareScoreState = compareScoreViewModel.getState();
        compareScoreState.setQuizName(compareScoreOutputData.getQuizName());
        compareScoreState.setQuizResults(compareScoreOutputData.getQuizResults());
        compareScoreState.setNormalizedQuizResults(compareScoreOutputData.getNormalizedQuizResults());
        compareScoreViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    @Override
    public void switchToUserScoreView() {
        // TODO: Add transition work to go back to score view
        compareScoreViewModel.setState(new CompareScoreState());
        viewManagerModel.setState("view Score");
        viewManagerModel.firePropertyChange();
    }
}
