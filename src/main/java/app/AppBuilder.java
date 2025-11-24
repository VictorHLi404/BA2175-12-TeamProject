package app;

import entities.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuPresenter;
import interface_adapter.main_menu.MainMenuViewModel;
import interface_adapter.play.PlayQuizController;
import interface_adapter.play.PlayQuizPresenter;
import interface_adapter.play.PlayQuizViewModel;
import interface_adapter.session.SessionManager;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_score.ViewScoreController;
import interface_adapter.view_score.ViewScorePresenter;
import interface_adapter.view_score.ViewScoreViewModel;
import persistence.DataStore;
import persistence.FileReaderGateway;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.play.PlayQuizInputBoundary;
import use_case.play.PlayQuizInteractor;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.main_menu.MainMenuInputBoundary;
import use_case.main_menu.MainMenuInteractor;
import use_case.main_menu.MainMenuOutputBoundary;
import use_case.view_score.ViewScoreInputBoundary;
import use_case.view_score.ViewScoreInteractor;
import use_case.view_score.ViewScoreOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private MainMenuView mainMenuView;
    private MainMenuViewModel mainMenuViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private PlayQuizView playQuizView;
    private PlayQuizViewModel playQuizViewModel;

    private ViewScoreViewModel viewScoreViewModel;
    private ViewScoreView viewScoreView;

    private SessionManager currentSession = new SessionManager();

    private final DataStore userDataWriteObject = new JsonFileDataStore();
    private final FileReaderGateway userDataReadObject = new JsonFileReader();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addMainMenuView() {
        mainMenuViewModel = new MainMenuViewModel();
        mainMenuView = new MainMenuView(mainMenuViewModel,viewManagerModel);
        cardPanel.add(mainMenuView, mainMenuView.getViewName());

        MainMenuPresenter mainMenuPresenter = new MainMenuPresenter(mainMenuViewModel, viewManagerModel);
        MainMenuInteractor mainMenuInteractor = new MainMenuInteractor(mainMenuPresenter);
        MainMenuController mainMenuController = new MainMenuController(mainMenuInteractor);
        mainMenuView.setMainMenuController(mainMenuController);

        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());

        return this;
    }

    public AppBuilder addViewScoreComponents() {
        viewScoreViewModel = new ViewScoreViewModel();

        ViewScoreOutputBoundary viewScoreOutputBoundary =
                new ViewScorePresenter(viewScoreViewModel, mainMenuViewModel, viewManagerModel);

        ViewScoreInputBoundary viewScoreInputBoundary =
                new ViewScoreInteractor(userDataReadObject, viewScoreOutputBoundary);

        ViewScoreController viewScoreController =
                new ViewScoreController(viewScoreInputBoundary);

        viewScoreView = new ViewScoreView(viewScoreViewModel, viewManagerModel);
        viewScoreView.setViewScoreController(viewScoreController);

        cardPanel.add(viewScoreView, "view Score");
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary =
                new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        final SignupInputBoundary signupInteractor =
                new SignupInteractor(userDataReadObject, userDataWriteObject, signupOutputBoundary);
        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary =
                new LoginPresenter(viewManagerModel, mainMenuViewModel, loginViewModel,viewScoreViewModel);

        final LoginInputBoundary loginInteractor =
                new LoginInteractor(userDataReadObject, userDataWriteObject, loginOutputBoundary, currentSession);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }
    public AppBuilder addMainMenuUseCases() {
        final MainMenuOutputBoundary mainMenuOutputBoundary = new MainMenuPresenter(mainMenuViewModel,viewManagerModel,viewScoreViewModel);
        final MainMenuInputBoundary mainMenuInteractor = new MainMenuInteractor(mainMenuOutputBoundary);

        MainMenuController mainMenuController = new MainMenuController(mainMenuInteractor);
        mainMenuView.setMainMenuController(mainMenuController);
        return this;
    }

    public AppBuilder addPlayQuizView() {
        playQuizViewModel = new PlayQuizViewModel();
        playQuizView = new PlayQuizView(playQuizViewModel.getState(), null); // controller set later
        cardPanel.add(playQuizView, "playQuiz");
        return this;
    }

    public AppBuilder addPlayQuizUseCase() {
        // presenter
        PlayQuizPresenter presenter = new PlayQuizPresenter(playQuizViewModel);

        // interactor
        PlayQuizInputBoundary interactor =
                new PlayQuizInteractor(
                        userDataReadObject,   // FileReaderGateway
                        userDataWriteObject,  // DataStore
                        presenter,            // OutputBoundary
                        currentSession        // SessionManager
                );

        // controller
        PlayQuizController controller = new PlayQuizController(interactor);

        // connect controller to view
        playQuizView.setPlayQuizController(controller);

        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.GRAY);
        application.setContentPane(background);
        application.add(cardPanel);

        viewManagerModel.firePropertyChange();
        return application;
    }

}