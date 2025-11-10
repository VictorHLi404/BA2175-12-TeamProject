package persistence;

import com.google.gson.Gson;
import entities.Question;
import entities.Quiz;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void testReadQuizDataFromFile() throws IOException {
        // Arrange – create one quiz and write manually to file
        Question q1 = new Question("multiple", "easy", "1 + 1 = ?", List.of("1", "2", "3"), "2", false);

        Quiz quiz = new Quiz(1, new ArrayList<>(List.of(q1)), true, 1);
        Map<Integer, Quiz> quizzes = new HashMap<>();
        quizzes.put(quiz.getId(), quiz);

        // Write valid JSON to file
        try (FileWriter writer = new FileWriter("data/quizzes.json")) {
            gson.toJson(quizzes, writer);
        }

        Quiz loadedQuiz = reader.loadQuiz(1);

        assertNotNull(loadedQuiz, "Quiz should be loaded successfully");
        assertEquals(1, loadedQuiz.getId(), "Quiz ID should match");
        assertTrue(loadedQuiz.getIsCustom(), "Custom flag should match");
        assertEquals(1, loadedQuiz.getLength(), "Quiz length should match");

        assertNotNull(loadedQuiz.getQuestions(), "Questions list should not be null");
        assertEquals(1, loadedQuiz.getQuestions().size(), "Should have exactly 1 question");

        Question loadedQuestion = loadedQuiz.getQuestions().get(0);
        assertEquals("multiple", loadedQuestion.getFormat());
        assertEquals("easy", loadedQuestion.getDifficulty());
        assertEquals("1 + 1 = ?", loadedQuestion.getQuestion());
        assertEquals(List.of("1", "2", "3"), loadedQuestion.getChoices());
        assertEquals("2", loadedQuestion.getCorrectChoice());
        assertFalse(loadedQuestion.getIsCustom());
    }




}
