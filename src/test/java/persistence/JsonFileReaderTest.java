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
        reader = new JsonFileReader(true);
    }

    @Test
    void testSaveAndLoadUser() {
        DataStore store = new JsonFileDataStore(true);

        User u = new User("alice", "123456");

        store.saveUser(u);

        User loaded = store.loadUser("alice");

        assertNotNull(loaded);
        assertEquals("alice", loaded.getUsername());
        assertEquals("123456", loaded.getPassword());
    }

    @Test
    void testReadQuizAndQuestionDataFromFile() throws IOException {
        // Arrange – create one quiz and write manually to file
        Question q1 = new Question("multiple", "easy", "1 + 1 = ?", List.of("1", "2", "3"), "2", false, "Science: Mathematics");
        List<UUID> questions = new ArrayList<>();
        UUID questiondId = q1.getQuestionId();
        questions.add(q1.getQuestionId());
        Quiz quiz = new Quiz(questions, true, 1);
        UUID quizId = quiz.getQuizId();
        Map<UUID, Quiz> quizzes = new HashMap<>();
        quizzes.put(quiz.getQuizId(), quiz);
        Map<UUID, Question> questions_store = new HashMap<>();
        questions_store.put(q1.getQuestionId(), q1);
        DataStore store = new JsonFileDataStore(true);

        store.saveQuestion(q1);
        store.saveQuiz(quiz);

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
}
