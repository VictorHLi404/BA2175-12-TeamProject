package entities;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;



public class QuestionTest {

    private Question question;
    private List<String> choices;


    public void setUp() {
        choices = Arrays.asList("A", "B", "C", "D");
        question = new Question("Multiple Choice", "Easy", "What is Canada's capital city?",
                                choices, "A", false, "General Knowledge");

    }

    @Test
    public void testUUIDIsUnique() {
        Question q2 = new Question(
                "Multiple Choice",
                "Easy",
                "What is the largest country in the world?",
                choices,
                "A",
                false,
                "General Knowledge"
        );

        Assertions.assertNotEquals(question.getQuestionId(), q2.getQuestionId());
    }

    @Test
    public void testIsCorrect() {
        Assertions.assertTrue(question.isCorrect("A"));
        Assertions.assertFalse(question.isCorrect("B"));
    }

    @Test
    public void testEqualsAndHashCode() {
        Question q1 = new Question(
                "Multiple Choice", "Easy", "What is Canada's capital city?",
                choices, "A", false, "General Knowledge"
        );

        Assertions.assertEquals(question, q1);
        Assertions.assertEquals(question.hashCode(), q1.hashCode());

        Question q2 = new Question(
                "Multiple Choice",
                "Easy",
                "What is the largest country in the world?",
                choices,
                "A",
                false,
                "General Knowledge"
        );

        Assertions.assertNotEquals(question, q2);
        Assertions.assertNotEquals(question.hashCode(), q2.hashCode());
    }

    @Test
    public void testIsCorrectReturnsTrue() {
        Assertions.assertTrue(question.isCorrect("A"));
    }

    @Test
    public void testToStringFormat() {
        Assertions.assertEquals("Q: What is Canada's capital city? (Easy, Multiple Choice, General Knowledge)",
                                 question.toString());
    }
    

}
