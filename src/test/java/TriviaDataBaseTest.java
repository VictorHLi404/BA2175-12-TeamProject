import api.TriviaDataBase;
import entities.Question;

public class TriviaDataBaseTest {
    public static void main(String[] args) {
        TriviaDataBase db = new TriviaDataBase(new OkHttpConnection());

        try {
            Question[] questions = db.getQuestions(5, 9, "easy", "boolean");
            System.out.println("✅ Successfully fetched " + questions.length + " questions!");
            for (Question q : questions) {
                System.out.println(q);
            }
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
        }
    }
}
