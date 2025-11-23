package use_case.customize_quiz;

import entities.Question;
import java.util.List;
import java.io.IOException;

public interface CustomizeQuizDataAccessInterface {
    List<Question> fetchQuestions(int amount, String category, String difficulty, String type) throws IOException;
}
