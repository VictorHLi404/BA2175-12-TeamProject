package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_score.ViewScoreViewModel;
import use_case.main_menu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewScoreViewModel viewScoreViewModel;

    public MainMenuPresenter (MainMenuViewModel mainMenuViewModel, ViewManagerModel viewManagerModel, ViewScoreViewModel viewScoreViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewScoreViewModel = viewScoreViewModel;
    }

    @Override
    public void switchToPlayView() {
        viewManagerModel.setState("playQuiz");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToQuizCustomizationView() {
        // TODO: Put in code that swithces to the quiz customization view
    }

    @Override
    public void switchToPlayerHistoryView() {
        viewManagerModel.setState(viewScoreViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
