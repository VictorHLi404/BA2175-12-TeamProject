package interface_adapter;

public class CreateQuizState {

    private String quizName = "";
    private boolean quizSaved = false;
    private String message = "";

    public String getQuizName () {
        return quizName;
    }

    public void setQuizName (String quizName) {
        this.quizName = quizName;
    }

    public boolean isQuizSaved() {
        return quizSaved;
    }

    public void setQuizSaved(boolean quizSaved) {
        this.quizSaved = quizSaved;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

}
