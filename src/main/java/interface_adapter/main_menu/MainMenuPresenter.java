package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_score.ViewScoreViewModel;
import use_case.main_menu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public MainMenuPresenter (MainMenuViewModel mainMenuViewModel, ViewManagerModel viewManagerModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToPlayView() {
        viewManagerModel.setState("playQuiz");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToQuizCustomizationView() {
        viewManagerModel.setState("customize quiz");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToPlayerHistoryView() {
        viewManagerModel.setState("view Score");
        viewManagerModel.firePropertyChange();
    }
}
