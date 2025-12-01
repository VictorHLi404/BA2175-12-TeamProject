package use_case.login;

import entities.User;
import interface_adapter.session.SessionManager;
import persistence.DataStore;
import persistence.FileReaderGateway;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final DataStore userDataWriteObject;
    private final FileReaderGateway userDataReadObject;
    private final LoginOutputBoundary loginPresenter;
    private final SessionManager currentSession;

    public LoginInteractor(FileReaderGateway userDataReadObject,
                           DataStore userDataWriteObject,
                           LoginOutputBoundary loginOutputBoundary,
                           SessionManager currentSession) {
        this.userDataWriteObject = userDataWriteObject;
        this.userDataReadObject = userDataReadObject;
        this.loginPresenter = loginOutputBoundary;
        this.currentSession = currentSession;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (userDataReadObject.loadUser(username) == null) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataReadObject.loadUser(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataReadObject.loadUser(username);
                this.currentSession.setCurrentUser(user);

                final LoginOutputData loginOutputData = new LoginOutputData(user.getUsername());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}