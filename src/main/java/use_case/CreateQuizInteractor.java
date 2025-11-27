package use_case;

import entities.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizInteractor implements CreateQuizInputBoundary {

    private final CreateQuizUserDataAccessInterface DAO;
    private final CreateQuizOutputBoundary presenter;

    public CreateQuizInteractor(CreateQuizUserDataAccessInterface DAO, CreateQuizOutputBoundary presenter) {
        this.DAO = DAO;
        this.presenter = presenter;
    }

    public void execute(CreateQuizInputData inputData) {

        // Check if a quiz with the same name already exists
        if (DAO.quizExists(inputData.getQuizName())) {
            presenter.prepareFailView("A quiz with the same name already exists.");
            return;
        }

        // List to store the questions that are approved
        List<Question> validatedQuestions = getQuestions(inputData);

        // Check if there's at least one question in the quiz
        if (validatedQuestions.isEmpty()) {
            presenter.prepareFailView("A quiz must have at least 1 question!");
            return;
        }

            // Creating the Quiz object
            Quiz quiz = new Quiz(validatedQuestions, true, validatedQuestions.size());

            // Saving the quiz in the DAO
            DAO.saveQuiz(quiz);

            // Prepare success view
            presenter.prepareSuccessView(new CreateQuizOutputData(inputData.getQuizName()));

        }

    @NotNull
    private static List<Question> getQuestions(CreateQuizInputData inputData) {
        List<Question> validatedQuestions = new ArrayList<>();

        // Iterate through each question in the quiz
        for (QuestionInputData questionInput : inputData.getQuestions()) {

            // Returns the question string
            String questionText = questionInput.getQuestion();

            // Create a Question Object
            Question q = new Question(
                    questionInput.getFormat(),
                    questionInput.getDifficulty(),
                    questionInput.getQuestion(),
                    questionInput.getChoices(),
                    questionInput.getCorrectChoice(),
                    true
            );

            // Add q to the existing list of questions
            validatedQuestions.add(q);

        }
        return validatedQuestions;
    }

}
