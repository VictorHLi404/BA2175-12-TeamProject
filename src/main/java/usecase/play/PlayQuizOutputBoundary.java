package usecase.play;

import entities.Question;

public interface PlayQuizOutputBoundary {

    /**
     * Presents the first question immediately after a quiz starts.
     *
     * @param question       the question to display
     * @param questionIndex  the index of the question in the quiz sequence
     * @param answeredCount  how many questions have already been answered
     */
    void presentQuestion(Question question, int questionIndex, int answeredCount);

    /**
     * Switches the view to a multiple-choice question display.
     *
     * @param outputData structured data for presenting the question
     */
    void switchToMultipleChoiceView(PlayQuizOutputData outputData);

    /**
     * Switches the view to a true/false question display.
     *
     * @param outputData structured data for presenting the question
     */
    void switchToTrueFalseView(PlayQuizOutputData outputData);

    /**
     * Displays confirmation that the user selected the correct answer.
     *
     * @param outputData structured data describing the correctness result
     */
    void switchToCorrectAnswerView(PlayQuizOutputData outputData);

    /**
     * Displays feedback indicating the user selected an incorrect answer.
     *
     * @param outputData structured data describing the correctness result
     */
    void switchToIncorrectAnswerView(PlayQuizOutputData outputData);

    /**
     * Switches the UI to a quiz-completion view after all questions are answered.
     *
     * @param outputData structured data describing the final results
     */
    void switchToQuizOverView(PlayQuizOutputData outputData);

    /**
     * Presents an error message to the user.
     *
     * @param message the error description
     */
    void presentError(String message);
}
