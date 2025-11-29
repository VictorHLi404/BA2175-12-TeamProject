package interface_adapter.create_quiz;

public class CreateQuizViewModel extends ViewModel<CreateQuizState> {

    public static final String message_property = "message";
    public static final String quiz_saved_property = "quiz saved";

    public CreateQuizViewModel () {
        super("Create Quiz"); // Screen title
        setState(new CreateQuizState());    // Sets up data for the default screen
    }

    // This method is called by the presenter when the quiz is successfully created
    public void setQuizSaved (boolean quizSaved) {
        CreateQuizState currState = getState();
        currState.setQuizSaved(quizSaved);
        firePropertyChange(quiz_saved_property);
    }

    public void setMessage(String message) {
        CreateQuizState currState = getState();
        currState.setMessage(message);
        firePropertyChange(message_property);
    }

    // Calling on CreateQuizState
    public void setQuizName(String quizName) {
        CreateQuizState currState = getState();
        currState.setQuizName(quizName);
        firePropertyChange(quiz_saved_property);
    }

}
