package persistence;

import entities.Question;
import entities.QuizResults;
import entities.User;
import entities.Quiz;

import java.util.Map;
import java.util.UUID;

public interface DataStore {

    void saveUser(User user);

    User loadUser(String username);

    void saveQuiz(Quiz quiz);

    void saveQuestion(Question question);

    void saveQuizResults(QuizResults quizResults);

    Map<String, Integer> getCategoryToIdMapping();

    Map<Integer, String> getIdToCategoryMapping();
}
