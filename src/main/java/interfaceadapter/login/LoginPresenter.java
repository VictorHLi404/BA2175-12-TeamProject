package interfaceadapter.login;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.view_score.ViewScoreViewModel;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;
import interfaceadapter.main_menu.MainMenuViewModel;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewScoreViewModel viewScoreViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          MainMenuViewModel mainMenuViewModel,
                          LoginViewModel loginViewModel,
                          ViewScoreViewModel viewScoreViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
        this.viewScoreViewModel = viewScoreViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {

        // and clear everything from the LoginViewModel's state
        loginViewModel.setState(new LoginState());

        viewScoreViewModel.getState().setUsername(response.getUsername());
        viewScoreViewModel.firePropertyChange();
        // switch to the logged in view
        this.viewManagerModel.setState(mainMenuViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState("sign up");
        viewManagerModel.firePropertyChange();
    }
}