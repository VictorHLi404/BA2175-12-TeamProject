package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Question;
import entities.QuizResults;
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

    private final Gson gson = new Gson();
    private boolean isTestDatabase = false;

    public JsonFileDataStore() {
        // 确保 data 目录存在，不然写文件会报错
        File dir = new File(PathwayConstants.DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public JsonFileDataStore(boolean isTestDatabase) {
        this.isTestDatabase = isTestDatabase;
    }

    public String getPathway(String pathway) {
        if (isTestDatabase) {
            return PathwayConstants.TEST_DATA_DIR + pathway;
        }
        else {
            return PathwayConstants.DATA_DIR + pathway;
        }
    }
    // ================= 用户 =================

    public Map<String, Integer> getCategoryToIdMapping() {
        if (!Files.exists(Paths.get(PathwayConstants.CATEGORY_TO_ID_MAPPING_FILE))) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(PathwayConstants.CATEGORY_TO_ID_MAPPING_FILE)) {
            Type type = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> mapping = gson.fromJson(reader, type);
            return mapping != null ? mapping : new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, String> getIdToCategoryMapping() {
        if (!Files.exists(Paths.get(PathwayConstants.ID_TO_CATEGORY_MAPPING_FILE))) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(PathwayConstants.ID_TO_CATEGORY_MAPPING_FILE)) {
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
        writeJsonToFile(getPathway(PathwayConstants.USERS_FILE), allUsers);
    }

    @Override
    public User loadUser(String username) {
        Map<String, User> allUsers = loadAllUsers();
        return allUsers.get(username);
    }

    private Map<String, User> loadAllUsers() {
        if (!Files.exists(Paths.get(getPathway(PathwayConstants.USERS_FILE)))) {
            return new HashMap<>();
        }
        try (Reader reader = new FileReader(getPathway(PathwayConstants.USERS_FILE))) {
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
        JsonFileReader reader = new JsonFileReader(isTestDatabase);
        Map<UUID, Quiz> allQuizzes = reader.loadAllQuizzes();
        allQuizzes.put(quiz.getQuizId(), quiz);
        writeJsonToFile(getPathway(PathwayConstants.QUIZZES_FILE), allQuizzes);
    }

    // ================= 测验 =================
    @Override
    public void saveQuestion(Question question) {
        JsonFileReader reader = new JsonFileReader(isTestDatabase);
        Map<UUID, Question> allQuestions = reader.loadAllQuestions();
        allQuestions.put(question.getQuestionId(), question);
        writeJsonToFile(getPathway(PathwayConstants.QUESTIONS_FILE), allQuestions);
    }

    @Override
    public void saveQuizResults(QuizResults quizResults) {
        JsonFileReader reader = new JsonFileReader(isTestDatabase);
        Map<UUID, QuizResults> allQuizResults = reader.loadAllQuizResults();
        allQuizResults.put(quizResults.getQuizResultsId(),  quizResults);
        writeJsonToFile(getPathway(PathwayConstants.QUIZ_RESULTS_FILE), allQuizResults);
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
