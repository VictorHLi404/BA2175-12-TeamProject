package data_access;

import entity.Question;
import java.io.IOException;
import java.util.List;

public interface TriviaDataAccess {
    /**
     * Fetches a list of questions from the Open Trivia DB API.
     * @param amount number of questions to fetch
     * @param category optional category id (null if any)
     * @param difficulty optional difficulty ("easy", "medium", "hard")
     * @return list of Question entities
     */

    List<Question> fetchQuestions(int amount, String category, String difficulty, String type)
            throws IOException, InterruptedException;
}
