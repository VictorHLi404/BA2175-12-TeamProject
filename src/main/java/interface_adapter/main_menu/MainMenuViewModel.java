package interface_adapter.main_menu;

import interface_adapter.ViewModel;

public class MainMenuViewModel extends ViewModel {

    public static final String TITLE_LABEL = "Trivia Game";
    public static final String PLAY_BUTTON_LABEL = "PLAY";
    public static final String CREATE_QUIZ_BUTTON_LABEL = "CREATE QUIZ";
    public static final String VIEW_SCORES_BUTTON_LABEL = "VIEW SCORES";
    public MainMenuViewModel() {
        super("Main Menu");

    }
}
