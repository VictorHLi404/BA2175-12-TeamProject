package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main_menu.MainMenuViewModel;
import interface_adapter.session.SessionManager;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;

import interface_adapter.customize_quiz.CustomizeQuizController;
import interface_adapter.customize_quiz.CustomizeQuizPresenter;
import interface_adapter.customize_quiz.CustomizeQuizViewModel;

import use_case.customize_quiz.CustomizeQuizDataAccessInterface;
import use_case.customize_quiz.CustomizeQuizInputBoundary;
import use_case.customize_quiz.CustomizeQuizInteractor;
import use_case.customize_quiz.CustomizeQuizOutputBoundary;

import data_access.CustomizeQuizAPIDataAccessObject;
import view.CustomizeQuizView;

import persistence.DataStore;
import persistence.FileReaderGateway;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.LoginView;
import view.MainMenuView;
import view.SignupView;
import view.ViewManager;

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
    private CustomizeQuizView customizeQuizView;
    private CustomizeQuizViewModel customizeQuizViewModel;

    private SessionManager currentSession = new SessionManager();

    private final DataStore userDataWriteObject = new JsonFileDataStore();
    private final FileReaderGateway userDataReadObject = new JsonFileReader();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addMainMenuView() {
        mainMenuViewModel = new MainMenuViewModel();
        mainMenuView = new MainMenuView(mainMenuViewModel);
        cardPanel.add(mainMenuView, mainMenuView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());

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
                new LoginPresenter(viewManagerModel, mainMenuViewModel, loginViewModel);

        final LoginInputBoundary loginInteractor =
                new LoginInteractor(userDataReadObject, userDataWriteObject, loginOutputBoundary, currentSession);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addCustomizeQuizUseCase() {

        customizeQuizViewModel = new CustomizeQuizViewModel();

        CustomizeQuizOutputBoundary outputBoundary =
                new CustomizeQuizPresenter(customizeQuizViewModel);

        CustomizeQuizDataAccessInterface customizeQuizDAO =
                new CustomizeQuizAPIDataAccessObject();

        CustomizeQuizInputBoundary customizeQuizInteractor =
                new CustomizeQuizInteractor(customizeQuizDAO, outputBoundary);

        CustomizeQuizController customizeQuizController =
                new CustomizeQuizController(customizeQuizInteractor);

        customizeQuizView =
                new CustomizeQuizView(customizeQuizController, customizeQuizViewModel);

        cardPanel.add(customizeQuizView, customizeQuizView.getViewName());

        mainMenuView.addPlayAction(() -> {
            viewManagerModel.setState("customize quiz");
            viewManagerModel.firePropertyChange();
        });

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
