package usecase.play;

import java.util.ArrayList;
import java.util.List;

import entities.Question;
import entities.Quiz;
import entities.QuizResults;
import entities.User;
import interfaceadapter.session.SessionManager;
import persistence.DataStore;
import persistence.FileReaderGateway;

/**
 * Interactor responsible for executing quiz logic.
 */
public class PlayQuizInteractor implements PlayQuizInputBoundary {

    private final PlayQuizOutputBoundary presenter;
    private final SessionManager session;
    private List<Question> questions = new ArrayList<>();
    private int currentIndex;
    private final List<String> previousAnswers = new ArrayList<>();
    private int cumulativeScore;
    private boolean finished;
    private Quiz currentQuiz;
    private final DataStore userDataWriteObject;
    private final FileReaderGateway userDataReadObject;

    /**
     * Constructs a PlayQuizInteractor.
     *
     * @param presenter the output boundary
     * @param session session manager
     * @param userDataWriteObject writer for user data
     * @param userDataReadObject reader for user data
     */
    public PlayQuizInteractor(PlayQuizOutputBoundary presenter,
                              SessionManager session, DataStore userDataWriteObject,
                              FileReaderGateway userDataReadObject) {
        this.presenter = presenter;
        this.session = session;
        this.userDataWriteObject = userDataWriteObject;
        this.userDataReadObject = userDataReadObject;
    }

    /**
     * Start a quiz with a given set of questions (e.g., from CustomizeQuizViewModel).
     *
     * @param questions the list of questions to load for the quiz;
     *                  if null or empty, the quiz will start with no questions
     */
    public void setQuestions(List<Question> questions) {
        if (questions == null) {
            this.questions = new ArrayList<>();
        }
        else {
            this.questions = questions;
        }
        this.currentIndex = 0;
        this.previousAnswers.clear();

        if (!this.questions.isEmpty()) {
            final Question first = this.questions.get(0);
            presenter.presentQuestion(first, currentIndex, previousAnswers.size());
        }
    }

    /**
     * Executes logic for an answered question.
     *
     * @param input input data containing selected choice
     */
    @Override
    public void execute(PlayQuizInputData input) {

        final Question current = questions.get(this.currentIndex);
        final String userChoice = input.getSelectedChoice();

        // Evaluate correctness
        final boolean isCorrect = current.getCorrectChoice().equals(userChoice);

        // Update cumulative score
        if (isCorrect) {
            cumulativeScore++;
        }

        // Save the answer
        previousAnswers.add(userChoice);

        // Prepare output for presenter
        final PlayQuizOutputData outputData = new PlayQuizOutputData(
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
        }
        else if ("boolean".equalsIgnoreCase(current.getFormat())) {
            presenter.switchToTrueFalseView(outputData);
        }

        if (isCorrect) {
            presenter.switchToCorrectAnswerView(outputData);
        }
        else {
            presenter.switchToIncorrectAnswerView(outputData);
        }

        this.currentIndex++;
    }

    /**
     * Starts a customized quiz using manually chosen questions.
     *
     * @param cquestions list of chosen questions
     * @param quiz quiz being played
     */
    public void startCustomizedQuiz(List<Question> cquestions, Quiz quiz) {
        if (cquestions == null || cquestions.isEmpty()) {
            return;
        }

        reset();
        this.questions = cquestions;
        this.currentQuiz = quiz;

        for (Question question : cquestions) {
            userDataWriteObject.saveQuestion(question);
        }
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
            final User currentUser = session.getCurrentUser();
            if (currentUser != null && currentQuiz != null) {
                final QuizResults results = new QuizResults(currentQuiz, currentUser.getUserId(), previousAnswers,
                        questions);
                try {
                    userDataWriteObject.saveQuizResults(results);
                }
                catch (Exception ex) {
                    presenter.presentError("Failed to save quiz results: " + ex.getMessage());
                    return;
                }
            }

            final Question last = questions.get(questions.size() - 1);
            final PlayQuizOutputData outputData = new PlayQuizOutputData(
                    false,
                    last.getCorrectChoice(),
                    cumulativeScore,
                    true,
                    "",
                    List.of(),
                    "",
                    ""
            );
            presenter.switchToQuizOverView(outputData);
            return;
        }

        final Question q = questions.get(currentIndex);
        final PlayQuizOutputData outputData = new PlayQuizOutputData(
                false,
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
        }
        else if ("boolean".equalsIgnoreCase(q.getFormat())) {
            presenter.switchToTrueFalseView(outputData);
        }

    }

    /**
     * Resets progress so a new quiz can begin.
     */
    public void reset() {
        this.currentIndex = 0;
        this.cumulativeScore = 0;
        this.previousAnswers.clear();
    }

}
