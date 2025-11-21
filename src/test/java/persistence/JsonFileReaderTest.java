package persistence;

import com.google.gson.Gson;
import entities.Question;
import entities.Quiz;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



public class JsonFileReaderTest {

    private final Gson gson = new Gson();
    private JsonFileReader reader;

    @BeforeEach
    void setUp() {
        new java.io.File("data").mkdirs();
        reader = new JsonFileReader();
    }


    @AfterEach
    void cleanUp() {
        // Clean up test files after each test
        new File("data/users.json").delete();
        new File("data/quizzes.json").delete();
    }

    @Test
    void testSaveAndLoadUser() {
        DataStore store = new JsonFileDataStore();

        User u = new User("alice", "123456");

        store.saveUser(u);

        User loaded = store.loadUser("alice");

        assertNotNull(loaded);
        assertEquals("alice", loaded.getUsername());
        assertEquals("123456", loaded.getPassword());
    }

    @Test
    void testLoadAllQuizzesWhenFileDoesNotExist() {
        java.io.File f = new java.io.File("data/quizzes.json");
        if (f.exists()) f.delete();

        var quizzes = reader.loadAllQuizzes();

        assertNotNull(quizzes, "Returned map should not be null");
        assertTrue(quizzes.isEmpty(), "Map should be empty when file does not exist");
    }

    @Test
    void testReadQuizAndQuestionDataFromFile() throws IOException {
        // Arrange – create one quiz and write manually to file
        Question q1 = new Question("multiple", "easy", "1 + 1 = ?", List.of("1", "2", "3"), "2", false);
        List<UUID> questions = new ArrayList<>();
        UUID questiondId = q1.getQuestionId();
        questions.add(q1.getQuestionId());
        Quiz quiz = new Quiz(questions, true, 1);
        UUID quizId = quiz.getQuizId();
        Map<UUID, Quiz> quizzes = new HashMap<>();
        quizzes.put(quiz.getQuizId(), quiz);
        Map<UUID, Question> questions_store = new HashMap<>();
        questions_store.put(q1.getQuestionId(), q1);

        // Write valid JSON to file
        try (FileWriter writer = new FileWriter("data/quizzes.json")) {
            gson.toJson(quizzes, writer);
        }
        try (FileWriter writer = new FileWriter("data/questions.json")) {
            gson.toJson(questions_store, writer);
        }

        Quiz loadedQuiz = reader.loadQuiz(quizId);

        assertNotNull(loadedQuiz, "Quiz should be loaded successfully");
        assertEquals(quizId, loadedQuiz.getQuizId(), "Quiz ID should match");
        assertTrue(loadedQuiz.getIsCustom(), "Custom flag should match");
        assertEquals(1, loadedQuiz.getLength(), "Quiz length should match");

        assertNotNull(loadedQuiz.getQuestionIds(), "Questions list should not be null");
        assertEquals(1, loadedQuiz.getQuestionIds().size(), "Should have exactly 1 question");

        Question loadedQuestion = reader.loadQuestions(questiondId);
        assertEquals("multiple", loadedQuestion.getFormat());
        assertEquals("easy", loadedQuestion.getDifficulty());
        assertEquals("1 + 1 = ?", loadedQuestion.getQuestion());
        assertEquals(List.of("1", "2", "3"), loadedQuestion.getChoices());
        assertEquals("2", loadedQuestion.getCorrectChoice());
        assertFalse(loadedQuestion.getIsCustom());
    }

    void testLoadAllQuestionsWhenFileDoesNotExist() {
        java.io.File f = new java.io.File("data/questions.json");
        if (f.exists()) f.delete();

        var questions = reader.loadAllQuestions();

        assertNotNull(questions, "Returned map should not be null");
        assertTrue(questions.isEmpty(), "Map should be empty when file does not exist");
    }


}
