package persistence;

import entities.User;
import entities.Quiz;

public interface DataStore {


    void saveUser(User user);


    User loadUser(String username);


    void saveQuiz(Quiz quiz);


    Quiz loadQuiz(int quizId);
}
