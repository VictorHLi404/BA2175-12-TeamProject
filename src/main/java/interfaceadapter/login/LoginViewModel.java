package interfaceadapter.login;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    public static final String SUBTITLE_LABEL = "Login";

    public LoginViewModel() {
        super("log in");
        setState(new LoginState());
    }

}