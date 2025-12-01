package use_case.create_quiz;


public interface CreateQuizOutputBoundary {

    void prepareSuccessView (CreateQuizOutputData outputData);

    void prepareFailView (String errorMessage);

    void switchToUserScoreView();

}