package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Quiz> playedQuizzes;
    private List<Quiz> createdQuizzes;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.playedQuizzes = new ArrayList<>();
        this.createdQuizzes = new ArrayList<>();
    }

    // getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<Quiz> getPlayedQuizzes() { return playedQuizzes; }
    public List<Quiz> getCreatedQuizzes() { return createdQuizzes; }

    // setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }


    public void createQuiz(Quiz quiz) {
        if (quiz != null) {
            createdQuizzes.add(quiz);
        }
    }

    public boolean playQuiz(Quiz quiz) {
        if (quiz != null && !playedQuizzes.contains(quiz)) {
            playedQuizzes.add(quiz);
            return true;
        }
        return false;
    }
}
