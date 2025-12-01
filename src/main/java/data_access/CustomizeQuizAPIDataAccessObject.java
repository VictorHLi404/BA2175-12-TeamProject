package data_access;

import api.TriviaDataBase;
import entities.Question;
import usecase.customize_quiz.CustomizeQuizDataAccessInterface;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomizeQuizAPIDataAccessObject implements CustomizeQuizDataAccessInterface {

    private final TriviaDataBase api = new TriviaDataBase();

    @Override
    public List<Question> fetchQuestions(int amount, String category, String difficulty, String type) throws IOException {
        Question[] arr = api.generateRandomQuestion(
                String.valueOf(amount),
                category,
                difficulty,
                type
        );
        return Arrays.asList(arr);
    }
}
