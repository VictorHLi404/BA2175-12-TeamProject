package persistence;

import entities.User;
import entities.Quiz;

import java.util.Map;
import java.util.UUID;

public interface DataStore {


    void saveUser(User user);


    User loadUser(String username);


    void saveQuiz(Quiz quiz);


    Quiz loadQuiz(UUID quizId);

    Map<String, Integer> getCategoryToIdMapping();

    Map<Integer, String> getIdToCategoryMapping();
}
