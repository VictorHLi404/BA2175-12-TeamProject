package interface_adapter.compare_score;

import interface_adapter.ViewManagerModel;
import use_case.compare_score.CompareScoreOutputBoundary;
import use_case.compare_score.CompareScoreOutputData;

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
        //TODO: Add transition work
        this.viewManagerModel.setState(compareScoreViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    @Override
    public void switchToUserScoreView() {
        // TODO: Add transition work to go back to score view
    }
}
