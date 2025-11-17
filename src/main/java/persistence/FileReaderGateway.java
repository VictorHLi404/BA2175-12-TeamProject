package persistence;

import entities.User;
import entities.Quiz;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FileReaderGateway {

    User loadUser(String username);

    Map<String, User> loadAllUsers();

    Quiz loadQuiz(UUID quizId);

    Map<UUID, Quiz> loadAllQuizzes();
}
