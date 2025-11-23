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


    public ViewScoreInputData(String username) {
        this.username = username;

    }

    String getUsername() {
        return username;
    }


}
