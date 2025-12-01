package usecase.view_score;

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
