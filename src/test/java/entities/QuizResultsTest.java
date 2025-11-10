package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizResultsTest {

    private List<Question> questions;

    @BeforeEach
    public void setUp() {
        Question q1 = new Question("multiple", "easy", "1 + 1 = ?", List.of("1", "2", "3"), "2", false);
        Question q2 = new Question("multiple", "medium", "Capital of France?", List.of("Paris", "London", "Berlin"), "Paris", true);
        Question q3 = new Question("boolean", "hard", "The sun is a star.", List.of("True", "False"), "True", false);

        questions = new ArrayList<>(List.of(q1, q2, q3));
    }

    @Test
    public void testAllAnswersCorrect() {
        List<String> answers = List.of("2", "Paris", "True");

        QuizResults results = new QuizResults(questions, answers);

        assertEquals(3, results.getScore());
    }

    @Test
    public void testSomeAnswersIncorrect() {
        List<String> answers = List.of("2", "London", "False");

        QuizResults results = new QuizResults(questions, answers);

        assertEquals(1, results.getScore());  // only first is correct
    }

    @Test
    public void testNoAnswersCorrect() {
        List<String> answers = List.of("3", "Berlin", "False");

        QuizResults results = new QuizResults(questions, answers);

        assertEquals(0, results.getScore());
    }

    @Test
    public void testMoreAnswersThanQuestions() {
        List<String> answers = List.of("2", "Paris", "True", "Extra");

        QuizResults results = new QuizResults(questions, answers);

        assertEquals(3, results.getScore());  //extra answer ignored
    }

    @Test
    public void testFewerAnswersThanQuestions() {
        List<String> answers = List.of("2", "Paris");

        QuizResults results = new QuizResults(questions, answers);

        assertEquals(2, results.getScore());  // only first two checked
    }

    @Test
    public void testEmptyLists() {
        QuizResults results = new QuizResults(new ArrayList<>(), new ArrayList<>());

        assertEquals(0, results.getScore());
    }
}
