package use_case.play;

import entities.Question;
import entities.Quiz;
import entities.QuizResults;
import entities.User;
import persistence.DataStore;
import persistence.FileReaderGateway;
import interface_adapter.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayQuizInteractor implements PlayQuizInputBoundary {

    private final FileReaderGateway reader;
    private final DataStore dataStore;
    private final PlayQuizOutputBoundary presenter;
    private final SessionManager session;

    public PlayQuizInteractor(FileReaderGateway reader,
                              DataStore dataStore,
                              PlayQuizOutputBoundary presenter,
                              SessionManager session) {
        this.reader = reader;
        this.dataStore = dataStore;
        this.presenter = presenter;
        this.session = session;
    }

    @Override
    public void execute(PlayQuizInputData input) {

        // Load quiz
        Quiz quiz = reader.loadQuiz(input.getQuizId());
        if (quiz == null) {
            presenter.presentError("Quiz not found.");
            return;
        }

        List<UUID> questionIds = quiz.getQuestionIds();
        int index = input.getQuestionIndex();

        // Validate question index
        if (index < 0 || index >= questionIds.size()) {
            presenter.presentError("Invalid question index.");
            return;
        }

        // Load the current question
        Question current = reader.loadQuestions(questionIds.get(index));
        if (current == null) {
            presenter.presentError("Question not found.");
            return;
        }

        // Check correctness
        boolean correct = current.isCorrect(input.getSelectedChoice());

        // Combine previous answers with current answer
        List<String> allAnswers = new ArrayList<>(input.getPreviousAnswers());
        allAnswers.add(input.getSelectedChoice());

        // Get current user from session
        User user = session.getCurrentUser();
        if (user == null) {
            presenter.presentError("No user logged in.");
            return;
        }

        // Compute cumulative score
        QuizResults tempResults = new QuizResults(quiz, user.getUserId(), allAnswers);
        int cumulativeScore = tempResults.getScore();

        // Determine if quiz is finished
        boolean finished = (index + 1) >= questionIds.size();

        // Prepare next question info
        String nextQuestionText = null;
        List<String> nextChoices = null;
        String nextQuestionFormat = null;
        if (!finished) {
            Question next = reader.loadQuestions(questionIds.get(index + 1));
            if (next != null) {
                nextQuestionText = next.getQuestion();
                nextChoices = next.getChoices();
                nextQuestionFormat = next.getFormat();
            }
        }

        // Prepare output data
        PlayQuizOutputData outputData = new PlayQuizOutputData(
                correct,
                cumulativeScore,
                finished,
                nextQuestionText,
                nextChoices,
                nextQuestionFormat
        );

        // Show the question (multiple choice or true/false)
        if (input.getSelectedChoice() == null || input.getSelectedChoice().isEmpty()) {
            if ("multiple choice".equalsIgnoreCase(current.getFormat())) {
                presenter.switchToMultipleChoiceView(outputData);
            } else if ("true/false".equalsIgnoreCase(current.getFormat())) {
                presenter.switchToTrueFalseView(outputData);
            }
            return; // wait for user to submit answer
        }

        // Otherwise, process answer and show correct/incorrect or quiz over
        if (finished) {
            presenter.switchToQuizOverView(outputData);
        } else {
            if (correct) {
                presenter.switchToCorrectAnswerView(outputData);
            } else {
                presenter.switchToIncorrectAnswerView(outputData);
            }
        }

        // Save QuizResults and update user if finished
        if (finished) {
            QuizResults finalResults = new QuizResults(quiz, user.getUserId(), allAnswers);
            dataStore.saveQuizResults(finalResults);

            // Update user's played quizzes
            user.playQuiz(quiz);
            dataStore.saveUser(user);
        }
    }
}
