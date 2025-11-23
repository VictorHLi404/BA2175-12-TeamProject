package use_case.customize_quiz;
import entities.Question;
import java.util.List;

public class CustomizeQuizOutputData {
    private final List<Question> customizedQuestions;
    private final String message;
    private final boolean success;

    public CustomizeQuizOutputData(List<Question> customizedQuestions, String message, boolean success) {
        this.customizedQuestions = customizedQuestions;
        this.message = message;
        this.success = success;
    }
    public List<Question> getCustomizedQuestions() { return customizedQuestions; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
}
