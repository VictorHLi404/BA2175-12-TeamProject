
import data_access.TriviaFileReader;
import entity.Question;
import java.util.List;

public class TriviaFileReaderTest {
    public static void main(String[] args) {
        TriviaFileReader reader = new TriviaFileReader();


        try {
            // Example 1: Fetch 5 easy multiple-choice questions
            List<Question> mcqQuestions = reader.fetchQuestions(5, null, "easy", "multiple");
            System.out.println("=== MULTIPLE CHOICE QUESTIONS ===");
            for (Question q : mcqQuestions) {
                System.out.println(q.getQuestion());
                System.out.println("Choices: " + q.getChoices());
                System.out.println("Answer: " + q.getCorrectChoice());
                System.out.println();
            }

            Thread.sleep(5000);

            // Example 2: Fetch 5 easy True/False questions
            List<Question> tfQuestions = reader.fetchQuestions(5, null, "easy", "boolean");
            System.out.println("=== TRUE / FALSE QUESTIONS ===");
            for (Question q : tfQuestions) {
                System.out.println(q.getQuestion());
                System.out.println("Choices: " + q.getChoices());
                System.out.println("Answer: " + q.getCorrectChoice());
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

