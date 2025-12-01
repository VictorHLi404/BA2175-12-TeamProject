package interface_adapter.main_menu;

import use_case.main_menu.MainMenuInputBoundary;

public class MainMenuController {
    private final MainMenuInputBoundary mainMenuInputBoundary;

    public MainMenuController(MainMenuInputBoundary mainMenuInputBoundary) {
        this.mainMenuInputBoundary = mainMenuInputBoundary;
    }

    public void switchToPlayView() {
        mainMenuInputBoundary.switchToPlayView();
    }

    public void switchToQuizCustomizationView() { mainMenuInputBoundary.switchToQuizCustomizationView(); }


    public void switchToViewScore() {
        mainMenuInputBoundary.switchToPlayerHistoryView();
    }

    public void switchToCreateQuizView() {mainMenuInputBoundary.switchToCreateQuizView();}
}
