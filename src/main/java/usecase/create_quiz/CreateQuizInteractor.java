package usecase.create_quiz;

import entities.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<Question> questions = getQuestions(inputData);
        List<UUID> validatedQuestionIDs = getQuestionIds(questions);

        // Check if there's at least one question in the quiz
        if (validatedQuestionIDs.isEmpty()) {
            presenter.prepareFailView("A quiz must have at least 1 question!");
            return;
        }

            // Creating the Quiz object
            Quiz quiz = new Quiz(validatedQuestionIDs, true, validatedQuestionIDs.size());
            quiz.setQuizName(inputData.getQuizName());
            // Saving the quiz in the DAO
            DAO.saveQuiz(quiz);

            for (Question question : questions) {
                DAO.saveQuestion(question);
            }

            // Prepare success view
            presenter.prepareSuccessView(new CreateQuizOutputData(inputData.getQuizName()));

        }

    @Override
    public void switchToUserScoreView() {
        presenter.switchToUserScoreView();
    }

    @NotNull
    private static List<Question> getQuestions(CreateQuizInputData inputData) {

        // Store the IDs of the questions that are approved
        List<Question> questions = new ArrayList<>();

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
                    true,
                    questionInput.getCategory()
            );

            // Store the question's ID
            questions.add(q);

        }
        return questions;
    }

    private static List<UUID> getQuestionIds(List<Question> questions) {
        List<UUID> ids = new ArrayList<>();
        for (Question q : questions) {
            ids.add(q.getQuestionId());
        }
        return ids;
    }

}
