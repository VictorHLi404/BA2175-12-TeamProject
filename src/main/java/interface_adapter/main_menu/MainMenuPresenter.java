package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import usecase.main_menu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public MainMenuPresenter (MainMenuViewModel mainMenuViewModel, ViewManagerModel viewManagerModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToPlayView() {
        // TODO: Put in code that switches to the play view
    }

    @Override
    public void switchToQuizCustomizationView() {
        // TODO: Put in code that swithces to the quiz customization view
    }

    @Override
    public void switchToPlayerHistoryView() {
        // TODO: Put in code that switches to the the player history view
    }
}
