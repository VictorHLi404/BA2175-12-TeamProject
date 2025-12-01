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
    private JButton playNow;
    private final JButton confirmPrevious;
    private JButton instructionsButton;
    private JButton applyButton;
    private JButton resetButton;

    private final DefaultComboBoxModel<String> previousQuizModel = new DefaultComboBoxModel<>();
    private JComboBox<String> difficulty;
    private JComboBox<String> type;
    private JComboBox<String> category;
    private JComboBox<String> previousQuizzes;

    public CustomizeQuizView(CustomizeQuizController controller, CustomizeQuizViewModel viewModel) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title
        JLabel title = new JLabel("Customize Quiz");
        title.setFont(new Font("Algerian", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createVerticalStrut(20));

        // Difficulty
        difficulty = new JComboBox<>(new String[]{
                "any difficulty", "easy", "medium", "hard"
        });
        difficulty.setMaximumSize(new Dimension(400, 30));
        difficulty.setFont(new Font("Algerian", Font.PLAIN, 14));
        add(createCenteredPanel(new JLabel("Difficulty:"), difficulty));
        add(Box.createVerticalStrut(10));

        // Type
        type = new JComboBox<>(new String[]{
                "any type", "multiple", "boolean"
        });
        type.setMaximumSize(new Dimension(400, 30));
        type.setFont(new Font("Algerian", Font.PLAIN, 14));
        add(createCenteredPanel(new JLabel("Type:"), type));
        add(Box.createVerticalStrut(10));

        // Category
        JsonFileDataStore dataStore = new JsonFileDataStore();
        Map<Integer, String> idToCategory = dataStore.getIdToCategoryMapping();
        String[] categoryNames = new String[idToCategory.size() + 1];
        categoryNames[0] = "any category";
        int i = 1;
        for (String name : idToCategory.values()) {
            categoryNames[i++] = name;
        }
        category = new JComboBox<>(categoryNames);
        category.setMaximumSize(new Dimension(400, 30));
        category.setFont(new Font("Algerian", Font.PLAIN, 14));
        add(createCenteredPanel(new JLabel("Category:"), category));
        add(Box.createVerticalStrut(100));

        // Buttons
        applyButton = createStyledButton("Apply", 200, 40);
        resetButton = createStyledButton("Reset to Default", 200, 40);
        playNow = createStyledButton("Play Now", 200, 40);
        confirmPrevious = createStyledButton("Play Previous Quiz", 200, 40);
        instructionsButton = createStyledButton("Instructions", 200, 40);

        // Stack buttons vertically and center
        add(createCenteredPanel(null, applyButton));
        add(Box.createVerticalStrut(20));
        add(createCenteredPanel(null, resetButton));
        add(Box.createVerticalStrut(20));
        add(createCenteredPanel(null, playNow));
        add(Box.createVerticalStrut(20));
        add(createCenteredPanel(null, confirmPrevious));
        add(Box.createVerticalStrut(20));
        add(createCenteredPanel(null, instructionsButton));


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
        applyButton.addActionListener(e -> {
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
        resetButton.addActionListener(e -> controller.resetCustomization());

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

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        styleButton(button, width, height);
        return button;
    }

    private void styleButton(JButton button, int width, int height) {
        button.setFocusPainted(false);
        button.setFont(new Font("Algerian", Font.PLAIN, 18));
        button.setBackground(new Color(230, 230, 230));
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setPreferredSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel createCenteredPanel(JComponent label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if (label != null) {
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createVerticalStrut(5));
        }
        if (field != null) {
            field.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(field);
        }
        return panel;
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
