import api.TriviaDataBase;
import entities.Question;
import org.junit.jupiter.api.Test;

public class TriviaDataBaseTest {
    public static void main(String[] args) {

    }

    @Test
    public void testGenerateQuestionFullParameters() {
        TriviaDataBase db = new TriviaDataBase();

        try {
            Question[] questions = db.generateRandomQuestion("5", "9", "easy", "boolean");
            System.out.println("Successfully fetched " + questions.length + " questions!");
            for (Question q : questions) {
                System.out.println(q);
                System.out.println(q.getChoices());
                System.out.println("Correct: " + q.getQuestion());
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testGenerateRandomQuestion() {
        // try default using null parameter
        TriviaDataBase db = new TriviaDataBase();

        try {
            Question[] questions = db.generateRandomQuestion();
            System.out.println("Successfully fetched " + questions.length + " questions!");
            for (Question q : questions) {
                System.out.println(q);
                System.out.println(q.getChoices());
                System.out.println("Correct: " + q.getCorrectChoice());
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateWithNull() {
        TriviaDataBase db = new TriviaDataBase();

        try {
            Question[] questions = db.generateRandomQuestion("5",null,null, null);
            System.out.println("Successfully fetched " + questions.length + " questions!");
            for (Question q : questions) {
                System.out.println(q);
                System.out.println(q.getChoices());
                System.out.println("Correct: " + q.getCorrectChoice());
            }
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }
}
