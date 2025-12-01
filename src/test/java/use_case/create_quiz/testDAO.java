package use_case.create_quiz;

import entities.Quiz;

import java.util.ArrayList;
import java.util.List;

// We don't want to write to actual memory each time we run the interactor test, hence we create a dummy DAO to temporarily store data
public class testDAO implements CreateQuizUserDataAccessInterface {

    // Temporarily stores Quiz objects and quiz names in memory
    List<Quiz> quizzes = new ArrayList<>();
    List<String> quizNames = new ArrayList<>();

    @Override
    public boolean quizExists(String quizName) {
        return quizNames.contains(quizName);
    }

    @Override
    public void saveQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

}
