package usecase.play;

import java.util.List;

public class PlayQuizOutputData {

    private final boolean correct;
    private final String correctAnswer;
    private final int cumulativeScore;
    private final boolean finished;

    private final String nextQuestionText;
    private final List<String> nextChoices;
    private final String questionFormat;
    private final String category;

    public PlayQuizOutputData(boolean correct, String correctAnswer,
                              int cumulativeScore,
                              boolean finished,
                              String nextQuestionText,
                              List<String> nextChoices,
                              String questionFormat, String category) {
        this.correct = correct;
        this.correctAnswer = correctAnswer;
        this.cumulativeScore = cumulativeScore;
        this.finished = finished;
        this.nextQuestionText = nextQuestionText;
        this.nextChoices = nextChoices;
        this.questionFormat = questionFormat;
        this.category = category;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getCumulativeScore() {
        return cumulativeScore;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getNextQuestionText() {
        return nextQuestionText;
    }

    public List<String> getNextChoices() {
        return nextChoices;
    }

    public String getQuestionFormat() {
        return questionFormat;
    }

    public String getCategory() {
        return category;
    }
}
