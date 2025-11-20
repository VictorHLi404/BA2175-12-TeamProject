package use_case.login;

import entities.User;
import persistence.DataStore;
import persistence.FileReaderGateway;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final DataStore userDataWriteObject;
    private final FileReaderGateway userDataReadObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(FileReaderGateway userDataReadObject,
                           DataStore userDataWriteObject,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataWriteObject = userDataWriteObject;
        this.userDataReadObject = userDataReadObject;
        this.loginPresenter = loginOutputBoundary;
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

                //TODO: Implement logic to keep track of the current user throughout the application

                final LoginOutputData loginOutputData = new LoginOutputData(user.getUsername());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}