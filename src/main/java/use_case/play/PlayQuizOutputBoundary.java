package use_case.play;

public interface PlayQuizOutputBoundary {

    // Called when the use case wants to display a multiple choice question
    void switchToMultipleChoiceView(PlayQuizOutputData outputData);

    // Called when the use case wants to display a true/false question
    void switchToTrueFalseView(PlayQuizOutputData outputData);

    // Called when the user selects a correct answer
    void switchToCorrectAnswerView(PlayQuizOutputData outputData);

    // Called when the user selects an incorrect answer
    void switchToIncorrectAnswerView(PlayQuizOutputData outputData);

    // Called when the quiz is finished
    void switchToQuizOverView(PlayQuizOutputData outputData);

    // Called when an error occurs
    void presentError(String message);
}
