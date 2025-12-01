package app;

import entities.Question;
import entities.Quiz;
import interface_adapter.ViewManagerModel;
import interface_adapter.compare_score.CompareScoreController;
import interface_adapter.compare_score.CompareScorePresenter;
import interface_adapter.compare_score.CompareScoreViewModel;
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

import interface_adapter.create_quiz.CreateQuizPresenter;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.create_quiz.CreateQuizController;
import use_case.create_quiz.*;

import interface_adapter.customize_quiz.CustomizeQuizController;
import interface_adapter.customize_quiz.CustomizeQuizPresenter;
import interface_adapter.customize_quiz.CustomizeQuizViewModel;

import use_case.compare_score.CompareScoreInputBoundary;
import use_case.compare_score.CompareScoreInteractor;
import use_case.compare_score.CompareScoreOutputBoundary;
import use_case.create_quiz.CreateQuizUserDataAccessInterface;
import use_case.customize_quiz.CustomizeQuizDataAccessInterface;
import use_case.customize_quiz.CustomizeQuizInputBoundary;
import use_case.customize_quiz.CustomizeQuizInteractor;
import use_case.customize_quiz.CustomizeQuizOutputBoundary;

import data_access.CustomizeQuizAPIDataAccessObject;
import use_case.play.PlayQuizInputData;
import view.CustomizeQuizView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    private CompareScoreViewModel compareScoreViewModel;

    private CreateQuizView createQuizView;
    private CreateQuizViewModel createQuizViewModel;

    private CustomizeQuizView customizeQuizView;
    private CustomizeQuizViewModel customizeQuizViewModel;
    private ViewScoreViewModel viewScoreViewModel;
    private ViewScoreView viewScoreView;
    private CompareScoreView compareScoreView;

    private SessionManager currentSession = new SessionManager();

    private final DataStore userDataWriteObject = new JsonFileDataStore();
    private final FileReaderGateway userDataReadObject = new JsonFileReader();

    private final Map<String, UUID> previousQuizLookup = new HashMap<>();

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
        final MainMenuOutputBoundary mainMenuOutputBoundary = new MainMenuPresenter(mainMenuViewModel,viewManagerModel);
        final MainMenuInputBoundary mainMenuInteractor = new MainMenuInteractor(mainMenuOutputBoundary);

        MainMenuController mainMenuController = new MainMenuController(mainMenuInteractor);
        mainMenuView.setMainMenuController(mainMenuController);
        return this;
    }

    public AppBuilder addPlayQuizUseCase() {
        PlayQuizViewModel viewModel= new PlayQuizViewModel();

        // presenter
        PlayQuizPresenter presenter = new PlayQuizPresenter(viewModel, viewManagerModel);

        // interactor
        PlayQuizInputBoundary interactor =
                new PlayQuizInteractor(
                        presenter,            // OutputBoundary
                        currentSession,        // SessionManager
                        userDataWriteObject,
                        userDataReadObject
                );

        PlayQuizController controller = new PlayQuizController(interactor);

        playQuizView = new PlayQuizView(controller, viewModel, viewManagerModel);
        cardPanel.add(playQuizView, "playQuiz");

        customizeQuizView.addPlayNowAction(() -> {

            List<Question> customizedQuestions = customizeQuizViewModel.getQuestions();

            if (customizedQuestions != null && !customizedQuestions.isEmpty()) {

                // Convert questions → list of IDs
                List<UUID> ids = customizedQuestions.stream()
                        .map(Question::getQuestionId)
                        .toList();

                Quiz customQuiz = new Quiz(ids, true, ids.size());
                customQuiz.setQuizName("Generated Quiz " + customQuiz.getQuizId().toString().substring(0, 8));

                controller.startCustomizedQuiz(customizedQuestions, customQuiz);
            }

            viewManagerModel.setState("playQuiz");
            viewManagerModel.firePropertyChange();
        });

        customizeQuizView.addPlayPreviousAction(selectedLabel -> {
            if (selectedLabel == null || !previousQuizLookup.containsKey(selectedLabel)) {
                JOptionPane.showMessageDialog(cardPanel, "Please select a previous quiz to play.");
                return;
            }

            UUID quizId = previousQuizLookup.get(selectedLabel);
            entities.Quiz quiz = userDataReadObject.loadQuiz(quizId);

            if (quiz == null) {
                JOptionPane.showMessageDialog(cardPanel, "Unable to load the selected quiz.");
                return;
            }

            Map<UUID, entities.Question> allQuestions = userDataReadObject.loadAllQuestions();
            List<entities.Question> questions = quiz.getQuestionIds().stream()
                    .map(allQuestions::get)
                    .filter(Objects::nonNull)
                    .toList();

            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(cardPanel, "No questions found for this quiz.");
                return;
            }

            controller.startCustomizedQuiz(questions, quiz);
            viewManagerModel.setState("playQuiz");
            viewManagerModel.firePropertyChange();
        });
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
            refreshPreviousQuizOptions();
            viewManagerModel.setState("customize quiz");
            viewManagerModel.firePropertyChange();
        });

        return this;
    }

    public AppBuilder addCompareScoreView() {
        compareScoreViewModel = new CompareScoreViewModel();
        compareScoreView = new CompareScoreView(compareScoreViewModel);
        cardPanel.add(compareScoreView, "compare score");
        return this;
    }

    public AppBuilder addCompareScoreUseCase() {
        final CompareScoreOutputBoundary compareScoreOutputBoundary = new CompareScorePresenter(viewManagerModel, compareScoreViewModel);
        final CompareScoreInputBoundary compareScoreInteractor = new CompareScoreInteractor(userDataReadObject, compareScoreOutputBoundary);
        CompareScoreController compareScoreController = new CompareScoreController(compareScoreInteractor, currentSession);
        compareScoreView.setCompareScoreController(compareScoreController);
        viewScoreView.setCompareScoreController(compareScoreController);
        return this;
    }

    public AppBuilder addCreateQuizView() {
        createQuizViewModel = new CreateQuizViewModel();
        // CreateQuizView requires a controller and viewModel, but we set the controller to null for now
        createQuizView = new CreateQuizView(createQuizViewModel);
        cardPanel.add(createQuizView, "Create Quiz");
        return this;
    }

    public AppBuilder addCreateQuizUseCase() {

        CreateQuizUserDataAccessInterface createQuizDAO = new CreateQuizDAO();

        CreateQuizOutputBoundary presenter = new CreateQuizPresenter(viewManagerModel, createQuizViewModel);
        CreateQuizInputBoundary interactor = new CreateQuizInteractor(createQuizDAO, presenter);

        CreateQuizController controller = new CreateQuizController(interactor);

        createQuizView.setController(controller);

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
    /**
     * Refresh the list of previous quizzes for the current user and update the customize view combo box.
     */
    private void refreshPreviousQuizOptions() {
        previousQuizLookup.clear();

        if (!currentSession.isLoggedIn()) {
            customizeQuizView.setPreviousQuizzes(List.of("Please log in to see past quizzes"));
            return;
        }

        Map<UUID, entities.QuizResults> allQuizResults = userDataReadObject.loadAllQuizResults();
        UUID userId = currentSession.getCurrentUser().getUserId();

        List<String> labels = new ArrayList<>();

        for (entities.QuizResults results : allQuizResults.values()) {
            if (Objects.equals(results.getUserId(), userId)) {
                entities.Quiz quiz = userDataReadObject.loadQuiz(results.getQuizId());
                String quizName = quiz != null && quiz.getQuizName() != null
                        ? quiz.getQuizName()
                        : "Quiz " + results.getQuizId().toString().substring(0, 8);
                String label = quizName + " (" + results.getTimestamp() + ")";
                previousQuizLookup.put(label, results.getQuizId());
                labels.add(label);
            }
        }

        if (labels.isEmpty()) {
            labels.add("No previous quizzes available");
        }

        customizeQuizView.setPreviousQuizzes(labels);
    }

}