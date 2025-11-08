package api;

import entity.Question;

public interface TriviaFetcher {
    /**
     * Fetches a default set of questions.
     */
    Question[] generateRandomQuestion();

    /**
     * Fetches a customized set of questions.
     * @param amount Number of questions
     * @param category Category ID (OpenTDB categories)
     * @param difficulty Difficulty level ("easy", "medium", "hard")
     * @param type Question type ("multiple", "boolean")
     */
    Question[] generateRandomQuestion(int amount, int category, String difficulty, String type);
}
