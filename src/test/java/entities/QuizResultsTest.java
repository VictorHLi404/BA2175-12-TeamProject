package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class QuizResultsTest {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path QUESTIONS_FILE = DATA_DIR.resolve("questions.json");

    private List<UUID> questionsIds;
    private Quiz quiz;
    private JsonFileDataStore writer;
    private JsonFileReader reader;

    @BeforeEach
    public void setUp() throws IOException {

        writer = new JsonFileDataStore();
        Files.createDirectories(DATA_DIR);
        Files.deleteIfExists(QUESTIONS_FILE);

        Question q1 = new Question("multiple", "easy", "1 + 1 = ?",
                List.of("1", "2", "3"), "2", false, "Science: Mathemathics");
        Question q2 = new Question("multiple", "medium", "Capital of France?",
                List.of("Paris", "London", "Berlin"), "Paris", true, "Geography");
        Question q3 = new Question("boolean", "hard", "The sun is a star.",
                List.of("True", "False"), "True", false, "Science");

        writer.saveQuestion(q1);
        writer.saveQuestion(q2);
        writer.saveQuestion(q3);

        questionsIds = new ArrayList<>();
        questionsIds.add(q1.getQuestionId());
        questionsIds.add(q2.getQuestionId());
        questionsIds.add(q3.getQuestionId());
        quiz = new Quiz(questionsIds, true, questionsIds.size());
    }

    @Test
    public void testAllAnswersCorrect() {
        List<String> answers = List.of("2", "Paris", "True");

        QuizResults results = new QuizResults(quiz, UUID.randomUUID(), answers);

        assertEquals(3, results.getScore());
    }

    @Test
    public void testSomeAnswersIncorrect() {
        List<String> answers = List.of("2", "London", "False");

        QuizResults results = new QuizResults(quiz, UUID.randomUUID(), answers);

        assertEquals(1, results.getScore());  // only first is correct
    }

    @Test
    public void testNoAnswersCorrect() {
        List<String> answers = List.of("3", "Berlin", "False");

        QuizResults results = new QuizResults(quiz, UUID.randomUUID(), answers);

        assertEquals(0, results.getScore());
    }

    @Test
    public void testMoreAnswersThanQuestions() {
        List<String> answers = List.of("2", "Paris", "True", "Extra");

        QuizResults results = new QuizResults(quiz, UUID.randomUUID(), answers);

        assertEquals(3, results.getScore());  //extra answer ignored
    }

    @Test
    public void testFewerAnswersThanQuestions() {
        List<String> answers = List.of("2", "Paris");

        QuizResults results = new QuizResults(quiz, UUID.randomUUID(), answers);

        assertEquals(2, results.getScore());  // only first two checked
    }

}
