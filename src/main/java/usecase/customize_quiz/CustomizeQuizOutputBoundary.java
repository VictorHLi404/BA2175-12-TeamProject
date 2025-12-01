package usecase.customize_quiz;

public interface CustomizeQuizOutputBoundary {

    void prepareSuccessView(CustomizeQuizOutputData outputData);
    void prepareFailView(String error);
}

