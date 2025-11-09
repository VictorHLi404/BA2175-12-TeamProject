package api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Question;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TriviaDataBase {
    private static final String API_URL = "https://opentdb.com/api.php?";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String STATUS_CODE = "response_code";

    private static final int SUCCESS_CODE = 0;

    private static final int DEFAULT_AMOUNT = 10;
    private static final int DEFAULT_CATEGORY = 9;
    private static final String DEFAULT_DIFFICULTY = "medium"; // easy | medium | hard
    private static final String DEFAULT_TYPE = "multiple"; // multiple | boolean


    public Question[] generateRandomQuestion() {
        return generateRandomQuestion(DEFAULT_AMOUNT, DEFAULT_CATEGORY, DEFAULT_DIFFICULTY, DEFAULT_TYPE);
    }

    public Question[] generateRandomQuestion(int amount, int category, String difficulty, String type) {
        // Build the request to get questions.
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("%samount=%s&category=%s&difficulty=%s&type=%s",
                API_URL, amount, category, difficulty, type))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        //EX: https://opentdb.com/api.php?amount=10&category=16&difficulty=medium&type=multiple

        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new RuntimeException("Empty response from API");
            }

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE) == SUCCESS_CODE) {
                final JSONArray questions = responseBody.getJSONArray("results");
                final Question[] quiz = new Question[questions.length()];
                for (int i = 0; i < questions.length(); i++) {
                    final JSONObject questionJSON = questions.getJSONObject(i);
                    quiz[i] = new Question(questionJSON);
                }
                return quiz;
            } else {
                throw new RuntimeException("Unknown error");
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
