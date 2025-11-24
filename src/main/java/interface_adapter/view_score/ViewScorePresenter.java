package interface_adapter.view_score;

import interface_adapter.ViewManagerModel;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.view_score.ViewScoreOutputBoundary;
import use_case.view_score.ViewScoreOutputData;

public class ViewScorePresenter implements ViewScoreOutputBoundary {



    private final ViewScoreViewModel viewScoreViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewScorePresenter(ViewScoreViewModel viewScoreViewModel, MainMenuViewModel mainMenuViewModel, ViewManagerModel viewManagerModel) {
        this.viewScoreViewModel = viewScoreViewModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ViewScoreOutputData outputData) {
        final ViewScoreState viewScoreState = viewScoreViewModel.getState();
        viewScoreState.setScore(outputData.getScore());
        viewScoreState.setUsername(outputData.getUsername());
        viewScoreState.setViewMessage("Score: " + outputData.getScore());
        viewScoreViewModel.firePropertyChange();
    }

    @Override
    public void prepareNoResultsView(String username) {
        final ViewScoreState viewScoreState = viewScoreViewModel.getState();
        viewScoreState.setUsername(username);
        viewScoreState.setViewMessage(username + " has not played any quiz yet.");
        viewScoreViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final ViewScoreState viewScoreState = viewScoreViewModel.getState();
        viewScoreState.setScore(0);
        viewScoreState.setViewMessage("Error: " + error);
        viewScoreViewModel.firePropertyChange();
    }

    @Override
    public void switchToMainMenuView() {
        //System.out.println("Target View: " + mainMenuViewModel.getViewName());
        viewManagerModel.setState(mainMenuViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

}
