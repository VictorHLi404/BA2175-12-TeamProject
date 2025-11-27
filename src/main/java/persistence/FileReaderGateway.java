package persistence;

import entities.Question;
import entities.QuizResults;
import entities.User;
import entities.Quiz;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FileReaderGateway {

    User loadUser(String username);

    User loadUser(UUID userId);

    Map<String, User> loadAllUsers();

    Quiz loadQuiz(UUID quizId);

    Map<UUID, Quiz> loadAllQuizzes();

    QuizResults loadQuizResults(UUID quizResultsId);

    Map<UUID, QuizResults> loadAllQuizResults();

    Question loadQuestions(UUID questionId);

    Map<UUID, Question> loadAllQuestions();
}
