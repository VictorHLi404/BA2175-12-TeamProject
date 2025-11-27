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

    private final PlayQuizOutputBoundary presenter;
    private final SessionManager session;
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private final List<String> previousAnswers = new ArrayList<>();
    private int cumulativeScore = 0;
    private boolean finished;

    public PlayQuizInteractor(PlayQuizOutputBoundary presenter,
                              SessionManager session) {
        this.presenter = presenter;
        this.session = session;
    }

    /**
     * Start a quiz with a given set of questions (e.g., from CustomizeQuizViewModel)
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
        this.currentIndex = 0;
        this.previousAnswers.clear();

        if (!this.questions.isEmpty()) {
            Question first = this.questions.get(0);
            presenter.presentQuestion(first, currentIndex, previousAnswers.size());
        }
    }

    @Override
    public void execute(PlayQuizInputData input) {

        Question current = questions.get(this.currentIndex);
        String userChoice = input.getSelectedChoice();

        // Evaluate correctness
        boolean isCorrect = current.getCorrectChoice().equals(userChoice);

        // Update cumulative score
        if (isCorrect) cumulativeScore++;

        // Save the answer
        previousAnswers.add(userChoice);

        // Check if this was the last question
        boolean finished = this.currentIndex > questions.size() - 1;

        // Prepare output for presenter
        PlayQuizOutputData outputData = new PlayQuizOutputData(
                isCorrect,
                current.getCorrectChoice(),
                cumulativeScore,
                finished,
                current.getQuestion(),
                current.getChoices(),
                current.getFormat()
        );

        // Show result
        if (finished) {
            presenter.switchToQuizOverView(outputData);
        }

        if (isCorrect) {
            presenter.switchToCorrectAnswerView(outputData);
        } else {
            presenter.switchToIncorrectAnswerView(outputData);
        }

        if ("multiple".equalsIgnoreCase(current.getFormat())) {
            presenter.switchToMultipleChoiceView(outputData);
        } else if ("boolean".equalsIgnoreCase(current.getFormat())) {
            presenter.switchToTrueFalseView(outputData);
        }

//        this.currentIndex = index;
        this.currentIndex++;
    }

    public void startCustomizedQuiz(List<Question> questions) {
        if (questions == null || questions.isEmpty()) return;

        reset();
        this.questions = questions;

        loadNextQuestion();
    }

    @Override
    public void loadNextQuestion() {
        if (currentIndex >= questions.size()) return;

        Question q = questions.get(currentIndex);
        PlayQuizOutputData outputData = new PlayQuizOutputData(
                false,                // isCorrect placeholder (user hasn't answered yet)
                q.getCorrectChoice(),
                cumulativeScore,
                currentIndex == questions.size() - 1,
                q.getQuestion(),
                q.getChoices(),
                q.getFormat()
        );

        if ("multiple".equalsIgnoreCase(q.getFormat())) {
            presenter.switchToMultipleChoiceView(outputData);
        } else if ("boolean".equalsIgnoreCase(q.getFormat())) {
            presenter.switchToTrueFalseView(outputData);
        }

    }

    public void reset() {
        this.currentIndex = 0;
        this.cumulativeScore = 0;
        this.previousAnswers.clear();
    }

}
