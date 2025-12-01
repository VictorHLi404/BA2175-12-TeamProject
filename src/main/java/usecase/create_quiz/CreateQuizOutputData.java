package usecase.create_quiz;

public class CreateQuizOutputData {

    private final String quizName;

    public CreateQuizOutputData (String quizName) {
        this.quizName = quizName;
    }

    public String getQuizName () {
        return quizName;
    }
}
