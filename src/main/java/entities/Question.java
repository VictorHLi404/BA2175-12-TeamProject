package entities;

import java.util.List;

public class Question {
    private String format;
    private String difficulty;
    private String question;
    private List<String> choices;
    private String correctChoice;
    private Boolean isCustom;

    public Question(String format, String difficulty, String question, List<String> choices, String correctChoice
    , Boolean isCustom) {
        this.format = format;
        this.difficulty = difficulty;
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
        this.isCustom = isCustom;
    }

    // getters
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

    public boolean isCorrect(String choice) {
        return correctChoice.equals(choice);
    }
}