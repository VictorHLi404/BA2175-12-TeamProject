package usecase.signup;

import entities.User;
import persistence.FileReaderGateway;
import persistence.JsonFileDataStore;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final FileReaderGateway userDataReadObject;
    private final JsonFileDataStore userDataWriteObject;
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(FileReaderGateway userDataReadObject,
                            JsonFileDataStore userDataWriteObject,
                            SignupOutputBoundary signupOutputBoundary) {
        this.userDataReadObject = userDataReadObject;
        this.userDataWriteObject = userDataWriteObject;
        this.userPresenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataReadObject.loadUser(signupInputData.getUsername()) != null) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if ("".equals(signupInputData.getPassword())) {
            userPresenter.prepareFailView("New password cannot be empty");
        }
        else if ("".equals(signupInputData.getUsername())) {
            userPresenter.prepareFailView("Username cannot be empty");
        }
        else {
            final User user = new User(signupInputData.getUsername(), signupInputData.getPassword());
            userDataWriteObject.saveUser(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}