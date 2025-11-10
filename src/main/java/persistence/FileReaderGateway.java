package persistence;

import entities.User;
import entities.Quiz;
import java.util.List;
import java.util.Map;

public interface FileReaderGateway {

    User loadUser(String username);

    Map<String, User> loadAllUsers();

    Quiz loadQuiz(int quizId);

    Map<Integer, Quiz> loadAllQuizzes();
}
