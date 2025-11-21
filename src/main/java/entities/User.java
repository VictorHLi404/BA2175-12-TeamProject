package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID userId;
    private String username;
    private String password;
    private List<UUID> playedQuizzes;
    private List<UUID> createdQuizzes;

    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.playedQuizzes = new ArrayList<>();
        this.createdQuizzes = new ArrayList<>();
    }

    // getters

    public UUID getUserId() { return userId; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<UUID> getPlayedQuizzes() { return playedQuizzes; }
    public List<UUID> getCreatedQuizzes() { return createdQuizzes; }

    // setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }


    public void createQuiz(Quiz quiz) {
        if (quiz != null) {
            createdQuizzes.add(quiz.getQuizId());
        }
    }

    public boolean playQuiz(Quiz quiz) {

        if (quiz.getQuizId() != null && !playedQuizzes.contains(quiz.getQuizId())) {
            playedQuizzes.add(quiz.getQuizId());
            return true;
        }
        return false;
    }
}
