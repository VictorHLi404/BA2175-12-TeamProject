package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.User;
import entities.Quiz;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonFileDataStore implements DataStore {

    // 固定我们的目录和文件名
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.json";
    private static final String QUIZZES_FILE = DATA_DIR + "/quizzes.json";
    private static final String CATEGORY_TO_ID_MAPPING_FILE =  DATA_DIR + "/category_mapping/category_to_id_mapping.json";
    private static final String ID_TO_CATEGORY_MAPPING_FILE =  DATA_DIR + "/category_mapping/id_to_category_mapping.json";
    private final Gson gson = new Gson();

    public JsonFileDataStore() {
        // 确保 data 目录存在，不然写文件会报错
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    // ================= 用户 =================

    public Map<String, Integer> getCategoryToIdMapping() {
        if (!Files.exists(Paths.get(CATEGORY_TO_ID_MAPPING_FILE))) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(CATEGORY_TO_ID_MAPPING_FILE)) {
            Type type = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> mapping = gson.fromJson(reader, type);
            return mapping != null ? mapping : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, String> getIdToCategoryMapping() {
        if (!Files.exists(Paths.get(ID_TO_CATEGORY_MAPPING_FILE))) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(ID_TO_CATEGORY_MAPPING_FILE)) {
            Type type = new TypeToken<Map<Integer, String>>() {}.getType();
            Map<Integer, String> mapping = gson.fromJson(reader, type);
            return mapping != null ? mapping : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void saveUser(User user) {
        Map<String, User> allUsers = loadAllUsers();
        allUsers.put(user.getUsername(), user);
        writeJsonToFile(USERS_FILE, allUsers);
    }

    @Override
    public User loadUser(String username) {
        Map<String, User> allUsers = loadAllUsers();
        return allUsers.get(username);
    }

    private Map<String, User> loadAllUsers() {
        if (!Files.exists(Paths.get(USERS_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(USERS_FILE)) {
            Type type = new TypeToken<Map<String, User>>() {}.getType();
            Map<String, User> users = gson.fromJson(reader, type);
            return users != null ? users : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ================= 测验 =================

    @Override
    public void saveQuiz(Quiz quiz) {
        Map<UUID, Quiz> allQuizzes = loadAllQuizzes();
        allQuizzes.put(quiz.getQuizId(), quiz);
        writeJsonToFile(QUIZZES_FILE, allQuizzes);
    }

    @Override
    public Quiz loadQuiz(UUID quizId) {
        Map<UUID, Quiz> allQuizzes = loadAllQuizzes();
        return allQuizzes.get(quizId);
    }

    private Map<UUID, Quiz> loadAllQuizzes() {
        if (!Files.exists(Paths.get(QUIZZES_FILE))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(QUIZZES_FILE)) {
            Type type = new TypeToken<Map<UUID, Quiz>>() {}.getType();
            Map<UUID, Quiz> quizzes = gson.fromJson(reader, type);
            return quizzes != null ? quizzes : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ================= 通用写文件方法 =================

    private void writeJsonToFile(String filename, Object obj) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(obj, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
