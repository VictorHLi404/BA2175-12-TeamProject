package use_case.play;

import entities.Question;
import entities.Quiz;
import entities.QuizResults;
import entities.User;
import persistence.DataStore;
import persistence.FileReaderGateway;
import interface_adapter.session.SessionManager;
import persistence.JsonFileDataStore;

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
    private Quiz currentQuiz;
    private final DataStore userDataWriteObject;
    private final FileReaderGateway userDataReadObject;

    public PlayQuizInteractor(PlayQuizOutputBoundary presenter,
                              SessionManager session, DataStore userDataWriteObject, FileReaderGateway userDataReadObject) {
        this.presenter = presenter;
        this.session = session;
        this.userDataWriteObject = userDataWriteObject;
        this.userDataReadObject = userDataReadObject;
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

        // Prepare output for presenter
        PlayQuizOutputData outputData = new PlayQuizOutputData(
                isCorrect,
                current.getCorrectChoice(),
                cumulativeScore,
                false,
                current.getQuestion(),
                current.getChoices(),
                current.getFormat(),
                current.getCategory()
        );


        if ("multiple".equalsIgnoreCase(current.getFormat())) {
            presenter.switchToMultipleChoiceView(outputData);
        } else if ("boolean".equalsIgnoreCase(current.getFormat())) {
            presenter.switchToTrueFalseView(outputData);
        }

        if (isCorrect) {
            presenter.switchToCorrectAnswerView(outputData);
        } else {
            presenter.switchToIncorrectAnswerView(outputData);
        }

//        this.currentIndex = index;
        this.currentIndex++;
    }

    public void startCustomizedQuiz(List<Question> questions, Quiz quiz) {
        if (questions == null || questions.isEmpty()) return;

        reset();
        this.questions = questions;
        this.currentQuiz = quiz;
        userDataWriteObject.saveQuiz(quiz);

        loadNextQuestion();
    }

    @Override
    public void loadNextQuestion() {
        if (questions.isEmpty()) {
            presenter.presentError("No questions available for this quiz.");
            return;
        }

        if (currentIndex >= questions.size()) {
            // Generate QuizResults
            User currentUser = session.getCurrentUser();
            if (currentUser != null && currentQuiz != null) {
                QuizResults results = new QuizResults(currentQuiz, currentUser.getUserId(), previousAnswers, questions);
                try {
                    userDataWriteObject.saveQuizResults(results);
                } catch (Exception e) {
                    presenter.presentError("Failed to save quiz results: " + e.getMessage());
                    return;
                }
            }

            // No questions left, quiz is finished
            Question last = questions.get(questions.size() - 1); // last question reference
            PlayQuizOutputData outputData = new PlayQuizOutputData(
                    false, // last question already answered, correctness irrelevant here
                    last.getCorrectChoice(),
                    cumulativeScore,
                    true, // finished = true now
                    "",   // no new question text
                    List.of(), // no choices
                    "",
                    ""
            );
            presenter.switchToQuizOverView(outputData);
            return;
        }

        Question q = questions.get(currentIndex);
        PlayQuizOutputData outputData = new PlayQuizOutputData(
                false,                // isCorrect placeholder (user hasn't answered yet)
                q.getCorrectChoice(),
                cumulativeScore,
                currentIndex == questions.size() - 1,
                q.getQuestion(),
                q.getChoices(),
                q.getFormat(),
                q.getCategory()
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
