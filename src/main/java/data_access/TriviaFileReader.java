package data_access;

import entity.Question;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TriviaFileReader implements TriviaDataAccess {

    private static final String API_URL = "https://opentdb.com/api.php";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<Question> fetchQuestions(int amount, String category, String difficulty, String type)
            throws IOException, InterruptedException {

        StringBuilder urlBuilder = new StringBuilder(API_URL);
        urlBuilder.append("?amount=").append(amount);
        if (category != null && !category.isEmpty()) {
            urlBuilder.append("&category=").append(URLEncoder.encode(category, StandardCharsets.UTF_8));
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            urlBuilder.append("&difficulty=").append(difficulty);
        }
        if (type != null && !type.isEmpty()) {
            urlBuilder.append("&type=").append(type);
        }

        Request request = new Request.Builder()
                .url(urlBuilder.toString())
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // Handle rate limit gracefully
                if (response.code() == 429) {
                    System.out.println("Rate limit hit. Waiting 3 seconds before retry...");
                    Thread.sleep(5000);
                    return fetchQuestions(amount, category, difficulty, type); // retry once
                }
                throw new IOException("Unexpected code " + response);
            }

            JSONObject body = new JSONObject(response.body().string());
            JSONArray results = body.getJSONArray("results");
            List<Question> questions = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject q = results.getJSONObject(i);
                String format = q.getString("type");
                String diff = q.getString("difficulty");
                String questionText = q.getString("question");
                String correct = q.getString("correct_answer");

                JSONArray incorrect = q.getJSONArray("incorrect_answers");
                List<String> choices = new ArrayList<>();
                for (int j = 0; j < incorrect.length(); j++) {
                    choices.add(incorrect.getString(j));
                }
                choices.add(correct);
                Collections.shuffle(choices);

                questions.add(new Question(format, diff, questionText, choices, correct));
            }

            return questions;
        }

    }
}

