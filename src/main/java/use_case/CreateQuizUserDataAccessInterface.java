package use_case;

import entities.*;

// Called from the Interactor
public interface CreateQuizUserDataAccessInterface {

    // Checks if a quiz with the same name already exists
    boolean quizExists(String quizName);

    // Saves the quiz object
    void saveQuiz(Quiz quiz);

}
