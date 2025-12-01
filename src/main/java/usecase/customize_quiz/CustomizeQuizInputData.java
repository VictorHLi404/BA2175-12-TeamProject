package usecase.customize_quiz;

public class CustomizeQuizInputData {
    private final String difficulty;
    private final String type;
    private final String category;
    private final boolean resetToDefault;

    public CustomizeQuizInputData(String difficulty, String type, String category, boolean resetToDefault) {
        this.difficulty = difficulty;
        this.type = type;
        this.category = category;
        this.resetToDefault = resetToDefault;
    }

    public String getDifficulty() { return difficulty; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public boolean isResetToDefault() { return resetToDefault; }


}
