package interface_adapter.main_menu;

import usecase.main_menu.MainMenuInputBoundary;

public class MainMenuController {
    private final MainMenuInputBoundary mainMenuInputBoundary;

    public MainMenuController(MainMenuInputBoundary mainMenuInputBoundary) {
        this.mainMenuInputBoundary = mainMenuInputBoundary;
    }

    public void switchToPlayView() {
        mainMenuInputBoundary.switchToPlayView();
    }
}
