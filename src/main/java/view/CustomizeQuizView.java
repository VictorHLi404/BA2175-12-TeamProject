package view;

import interface_adapter.customize_quiz.CustomizeQuizController;
import interface_adapter.customize_quiz.CustomizeQuizViewModel;
import persistence.JsonFileDataStore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CustomizeQuizView extends JPanel {

    private final String viewName = "customize quiz";
    private final JButton playNow;
    private final JButton confirmPrevious;
    private final JButton instructionsButton;

    private final DefaultComboBoxModel<String> previousQuizModel = new DefaultComboBoxModel<>();
    private JComboBox<String> difficulty;
    private JComboBox<String> type;
    private JComboBox<String> category;
    private JComboBox<String> previousQuizzes;

    public CustomizeQuizView(CustomizeQuizController controller, CustomizeQuizViewModel viewModel) {

        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel title = new JLabel("Customize Quiz", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        // Difficulty
        add(new JLabel("Difficulty:"));
        difficulty = new JComboBox<>(new String[]{
                "any difficulty",
                "easy",
                "medium",
                "hard"
        });
        difficulty.setSelectedIndex(0);
        add(difficulty);

        // Type
        add(new JLabel("Type:"));
        type = new JComboBox<>(new String[]{
                "any type",
                "multiple",
                "boolean"
        });
        type.setSelectedIndex(0);
        add(type);

        // Category
        add(new JLabel("Category:"));
        JsonFileDataStore dataStore = new JsonFileDataStore();
        Map<Integer, String> idToCategory = dataStore.getIdToCategoryMapping();

        String[] categoryNames = new String[idToCategory.size() + 1];
        categoryNames[0] = "any category";
        int i = 1;
        for (String name : idToCategory.values()) {
            categoryNames[i++] = name;
        }

        category = new JComboBox<>(categoryNames);
        category.setSelectedIndex(0);
        add(category);

        // Buttons
        JButton apply = new JButton("Apply");
        JButton reset = new JButton("Reset to Default");

        playNow = new JButton("Play Now");
        confirmPrevious = new JButton("Play Previous Quiz");


        add(apply);
        add(reset);

        add(playNow);
        add(new JLabel("Previous Quizzes:"));
        previousQuizzes = new JComboBox<>(previousQuizModel);
        add(previousQuizzes);
        add(confirmPrevious);

        instructionsButton = new JButton("Instructions");
        add(instructionsButton);

// 再加监听
        instructionsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    InstructionsContent.getInstructions(),
                    "Instructions",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });


        // Apply customization
        apply.addActionListener(e -> {
            String difficultyVal = difficulty.getSelectedIndex() == 0 ? null : (String) difficulty.getSelectedItem();
            String typeVal = type.getSelectedIndex() == 0 ? null : (String) type.getSelectedItem();
            String categoryName = category.getSelectedIndex() == 0 ? null : (String) category.getSelectedItem();

            String categoryId = null;
            if (categoryName != null) {
                categoryId = dataStore.getCategoryToIdMapping().get(categoryName).toString();
            }

            controller.applyCustomization(
                    difficultyVal,
                    typeVal,
                    categoryId
            );
        });

        // Reset customization
        reset.addActionListener(e -> controller.resetCustomization());

        // ViewModel listener
        viewModel.addPropertyChangeListener(evt -> {

            JOptionPane.showMessageDialog(this, viewModel.getMessage());

            if (viewModel.isResetRequested()) {
                difficulty.setSelectedIndex(0);
                type.setSelectedIndex(0);
                category.setSelectedIndex(0);
            }
        });

    }

    public void addPlayNowAction(Runnable action) {
        playNow.addActionListener(e -> action.run());
    }

    public void setPreviousQuizzes(List<String> quizzes) {
        previousQuizModel.removeAllElements();
        for (String quiz : quizzes != null ? quizzes : new ArrayList<String>()) {
            previousQuizModel.addElement(quiz);
        }
    }

    public void addPlayPreviousAction(Consumer<String> action) {
        confirmPrevious.addActionListener(e -> {
            Object selection = previousQuizzes.getSelectedItem();
            action.accept(selection == null ? null : selection.toString());
        });
    }

    public String getViewName() {
        return viewName;
    }
}
