package usecase.create_quiz;

import java.util.List;

public class CreateQuizInputData {

    private final String quizName;
    private final List<QuestionInputData> questions;

    public CreateQuizInputData (String quizName, List<QuestionInputData> questions) {
        this.quizName = quizName;
        this.questions = questions;
    }

    public String getQuizName() {
        return quizName;
    }

    public List<QuestionInputData> getQuestions() {
        return questions;
    }

}
