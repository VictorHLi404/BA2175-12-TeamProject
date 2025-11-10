package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.User;
import entities.Quiz;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonFileReader implements FileReaderGateway {

    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.json";
    private static final String QUIZZES_FILE = DATA_DIR + "/quizzes.json";

    private final Gson gson = new Gson();

    @Override
    public User loadUser(String username) {
        Map<String, User> allUsers = loadAllUsers();
        return allUsers.get(username);
    }

    @Override
    public Map<String, User> loadAllUsers() {
        if (!Files.exists(Paths.get(USERS_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(USERS_FILE)) {
            Type type = new TypeToken<Map<String, User>>() {}.getType();
            Map<String, User> users = gson.fromJson(reader, type);
            return users != null ? users : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("Error reading users.json", e);
        }
    }

    @Override
    public Quiz loadQuiz(int quizId) {
        Map<Integer, Quiz> allQuizzes = loadAllQuizzes();
        return allQuizzes.get(quizId);
    }

    @Override
    public Map<Integer, Quiz> loadAllQuizzes() {
        if (!Files.exists(Paths.get(QUIZZES_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(QUIZZES_FILE)) {
            Type type = new TypeToken<Map<Integer, Quiz>>() {}.getType();
            Map<Integer, Quiz> quizzes = gson.fromJson(reader, type);
            return quizzes != null ? quizzes : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("Error reading quizzes.json", e);
        }
    }
}
