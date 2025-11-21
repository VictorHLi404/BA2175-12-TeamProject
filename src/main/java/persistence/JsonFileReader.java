package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Question;
import entities.QuizResults;
import entities.User;
import entities.Quiz;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonFileReader implements FileReaderGateway {

    private final Gson gson = new Gson();

    @Override
    public User loadUser(String username) {
        Map<String, User> allUsers = loadAllUsers();
        return allUsers.get(username);
    }

    @Override
    public Map<String, User> loadAllUsers() {
        if (!Files.exists(Paths.get(PathwayConstants.USERS_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(PathwayConstants.USERS_FILE)) {
            Type type = new TypeToken<Map<String, User>>() {}.getType();
            Map<String, User> users = gson.fromJson(reader, type);
            return users != null ? users : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error reading users.json", e);
        }

    }

    @Override
    public Quiz loadQuiz(UUID quizId) {
        Map<UUID, Quiz> allQuizzes = loadAllQuizzes();
        return allQuizzes.get(quizId);
    }

    @Override
    public Map<UUID, Quiz> loadAllQuizzes() {
        if (!Files.exists(Paths.get(PathwayConstants.QUIZZES_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(PathwayConstants.QUIZZES_FILE)) {
            Type type = new TypeToken<Map<UUID, Quiz>>() {}.getType();
            Map<UUID, Quiz> quizzes = gson.fromJson(reader, type);
            return quizzes != null ? quizzes : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error reading quizzes.json", e);
        }
    }

    @Override
    public QuizResults loadQuizResults(UUID quizResultsId) {
        Map<UUID, QuizResults> allQuizResults = loadAllQuizResults();
        return allQuizResults.get(quizResultsId);
    }

    @Override
    public Map<UUID, QuizResults> loadAllQuizResults() {
        if (!Files.exists(Paths.get(PathwayConstants.QUIZ_RESULTS_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(PathwayConstants.QUIZ_RESULTS_FILE)) {
            Type type = new TypeToken<Map<UUID, QuizResults>>() {}.getType();
            Map<UUID, QuizResults> questions = gson.fromJson(reader, type);
            return questions != null ? questions : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error reading questions.json", e);
        }
    }

    @Override
    public Question loadQuestions(UUID questionId) {
        Map<UUID, Question> allQuestions = loadAllQuestions();
        return allQuestions.get(questionId);
    }

    @Override
    public Map<UUID, Question> loadAllQuestions() {
        if (!Files.exists(Paths.get(PathwayConstants.QUESTIONS_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(PathwayConstants.QUESTIONS_FILE)) {
            Type type = new TypeToken<Map<UUID, Question>>() {}.getType();
            Map<UUID, Question> questions = gson.fromJson(reader, type);
            return questions != null ? questions : new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException("Error reading questions.json", e);
        }
    }
}
