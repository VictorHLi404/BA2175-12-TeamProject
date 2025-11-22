package use_case.view_score;

import entities.User;
import persistence.JsonFileReader;

import java.io.FileReader;
import java.util.UUID;

/**
 * The Input Data for the View Past Score Use Case.
 */
public class ViewScoreInputData {

    private final String username;
    private final UUID userId;


    public ViewScoreInputData(String username) {
        this.username = username;
        this.userId = getUserId();

    }

    String getUsername() {
        return username;
    }
    UUID getUserId() {
        JsonFileReader reader = new  JsonFileReader();
        User user = reader.loadUser(username);
        return user.getUserId();
    }

}
