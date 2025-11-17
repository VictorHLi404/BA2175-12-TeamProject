package entities;

import java.util.List;
import java.util.UUID;

public class Question {
    private UUID questionId;
    private String format;
    private String difficulty;
    private String question;
    private List<String> choices;
    private String correctChoice;
    private Boolean isCustom;

    public Question(String format, String difficulty, String question, List<String> choices, String correctChoice
    , Boolean isCustom) {
        this.questionId = UUID.randomUUID();
        this.format = format;
        this.difficulty = difficulty;
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
        this.isCustom = isCustom;
    }

    // getters
    public UUID getQuestionId() { return questionId; }
    public String getFormat() { return format; }
    public String getDifficulty() { return difficulty; }
    public String getQuestion() { return question; }
    public List<String> getChoices() { return choices; }
    public String getCorrectChoice() { return correctChoice; }
    public Boolean getIsCustom() { return isCustom; }

    @Override
    public String toString() {
        return String.format("Q: %s (%s, %s)", question, difficulty, format);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Question other = (Question) obj;

        return format.equals(other.format)
                && difficulty.equals(other.difficulty)
                && question.equals(other.question)
                && choices.equals(other.choices)
                && correctChoice.equals(other.correctChoice)
                && isCustom.equals(other.isCustom);
    }

    @Override
    public int hashCode() {
        int result = format.hashCode();
        result = 31 * result + difficulty.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + choices.hashCode();
        result = 31 * result + correctChoice.hashCode();
        result = 31 * result + isCustom.hashCode();
        return result;
    }

    public boolean isCorrect(String choice) {
        return correctChoice.equals(choice);
    }
}