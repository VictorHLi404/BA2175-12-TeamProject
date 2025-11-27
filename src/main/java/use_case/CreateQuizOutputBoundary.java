package use_case;


public interface CreateQuizOutputBoundary {

    void prepareSuccessView (CreateQuizOutputData outputData);

    void prepareFailView (String errorMessage);

}