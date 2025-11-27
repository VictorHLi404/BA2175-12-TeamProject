package use_case;

public interface CreateQuizInputBoundary {

    // Allows only CreateQuizInputData objects to be passed from the Controller to the Interactor
    void execute(CreateQuizInputData createQuizInputData);
}
