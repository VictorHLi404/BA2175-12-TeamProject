package usecase.main_menu;

public class MainMenuInteractor implements MainMenuInputBoundary {

    private final MainMenuOutputBoundary mainMenuOutputBoundary;

    public MainMenuInteractor(MainMenuOutputBoundary mainMenuOutputBoundary) {
        this.mainMenuOutputBoundary = mainMenuOutputBoundary;
    }
    @Override
    public void switchToPlayView() {
        mainMenuOutputBoundary.switchToPlayView();
    }

    @Override
    public void switchToQuizCustomizationView() {
        mainMenuOutputBoundary.switchToQuizCustomizationView();
    }

    @Override
    public void switchToPlayerHistoryView() {
        mainMenuOutputBoundary.switchToPlayerHistoryView();
    }
}
