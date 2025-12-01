package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.play.PlayQuizController;
import interface_adapter.play.PlayQuizState;
import interface_adapter.play.PlayQuizViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayQuizView extends JPanel {

    private final String viewName = "playQuiz";

    private final PlayQuizController controller;
    private final PlayQuizViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    // Labels
    private final JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel categoryLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel answerLabel = new JLabel("", SwingConstants.CENTER);

    // Panels for dynamic content
    private final JPanel multipleChoicePanel = new JPanel();
    private final JPanel trueFalsePanel = new JPanel();
    private final JPanel quizFinishedPanel = new JPanel();

    private final JButton submitButton = new JButton("Submit");
    private final JButton nextButton = new JButton("Next");

    private final JButton instructionsButton = new JButton("Instructions");

    private ButtonGroup buttonGroup;

    public PlayQuizView(PlayQuizController controller, PlayQuizViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Style labels
        styleLabel(questionLabel, 20, true);
        styleLabel(scoreLabel, 18, false);
        styleLabel(categoryLabel, 18, false);
        styleLabel(resultLabel, 18, true);
        styleLabel(answerLabel, 18, false);

        // Style buttons
        styleButton(submitButton, 200, 50);
        styleButton(nextButton, 200, 50);
        styleButton(instructionsButton, 200, 50);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        answerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // move under options

        nextButton.setVisible(false); // hidden until answer is submitted

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(categoryLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(answerLabel);
        centerPanel.add(Box.createVerticalStrut(10));

// Add dynamic panels (choices) first
        centerPanel.add(multipleChoicePanel);
        centerPanel.add(trueFalsePanel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(createCenteredPanel(submitButton));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(createCenteredPanel(nextButton));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(createCenteredPanel(instructionsButton));

        add(centerPanel, BorderLayout.CENTER);

        // Listen to ViewModel changes
        viewModel.addPropertyChangeListener(evt -> refreshView());

        instructionsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    InstructionsContent.getInstructions(),
                    "Instructions",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Submit button action
        submitButton.addActionListener(e -> onSubmit());

        // next button action
        nextButton.addActionListener(e -> onNext());

        refreshView();
    }

    private void onNext() {
        answerLabel.setVisible(false);
        answerLabel.setText("");
        resultLabel.setVisible(false);
        resultLabel.setText("");
        viewModel.getState().setLastAnswerCorrect(null);
        controller.nextQuestion();
    }

    private void onSubmit() {
        PlayQuizState state = viewModel.getState();

        if (buttonGroup == null || buttonGroup.getSelection() == null) return;

        String selectedChoice = buttonGroup.getSelection().getActionCommand();
        int currentIndex = state.getCurrentIndex();

        controller.execute(currentIndex, selectedChoice, List.of());

        submitButton.setEnabled(false);
        nextButton.setVisible(true);
    }

    private void buildMultipleChoicePanel(List<String> choices) {
        multipleChoicePanel.removeAll();
        buttonGroup = new ButtonGroup();
        multipleChoicePanel.setLayout(new GridLayout(choices.size(), 1, 5, 5));

        // Make a copy and shuffle it
        List<String> shuffledChoices = new ArrayList<>(choices);
        Collections.shuffle(shuffledChoices);

        for (String choice : shuffledChoices) {
            JRadioButton btn = new JRadioButton(choice);
            btn.setActionCommand(choice);
            buttonGroup.add(btn);
            multipleChoicePanel.add(btn);
        }
    }

    private void buildTrueFalsePanel(List<String> choices) {
        trueFalsePanel.removeAll();
        buttonGroup = new ButtonGroup();
        trueFalsePanel.setLayout(new GridLayout(choices.size(), 1, 5, 5));

        List<String> shuffledChoices = new ArrayList<>(choices);
        Collections.shuffle(shuffledChoices);

        for (String choice : shuffledChoices) {
            JRadioButton btn = new JRadioButton(choice);
            btn.setActionCommand(choice);
            buttonGroup.add(btn);
            trueFalsePanel.add(btn);
        }
    }

    private void buildQuizFinishedPanel(int score) {
        quizFinishedPanel.removeAll();
        quizFinishedPanel.setLayout(new BoxLayout(quizFinishedPanel, BoxLayout.Y_AXIS));

        JLabel finishedLabel = new JLabel("Quiz Over! Final score: " + score, SwingConstants.CENTER);
        styleLabel(finishedLabel, 20, true);
        JButton mainMenuBtn = new JButton("Back to Main Menu");
        styleButton(mainMenuBtn, 200, 50);
        mainMenuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuBtn.addActionListener(e -> {
            viewManagerModel.setState("Main Menu");
            viewManagerModel.firePropertyChange();
        });

        finishedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizFinishedPanel.add(Box.createVerticalStrut(20));
        quizFinishedPanel.add(finishedLabel);
        quizFinishedPanel.add(Box.createVerticalStrut(10));
        quizFinishedPanel.add(mainMenuBtn);
    }

    private void refreshView() {
        PlayQuizState state = viewModel.getState();

        // Restore visibility
        questionLabel.setVisible(true);
        scoreLabel.setVisible(true);

        submitButton.setVisible(true);
        submitButton.setEnabled(true);

        multipleChoicePanel.setVisible(false);
        trueFalsePanel.setVisible(false);
        quizFinishedPanel.setVisible(false);
        nextButton.setVisible(false);
        nextButton.setEnabled(true);

        answerLabel.setVisible(false);
        answerLabel.setText("");
        resultLabel.setVisible(false);
        resultLabel.setText("");

        questionLabel.setText("<html><h3>" + state.getQuestionText() + "</h3></html>");
        scoreLabel.setText("Score: " + state.getCumulativeScore());
        categoryLabel.setText("Category: " + state.getCategory());

        PlayQuizViewModel.PlayQuizMode mode = state.getMode(); // mode set by presenter

        switch (mode) {
            case MULTIPLE_CHOICE -> {
                questionLabel.setVisible(true);
                scoreLabel.setVisible(true);
                categoryLabel.setVisible(true);
                submitButton.setVisible(true);

                buildMultipleChoicePanel(state.getChoices());
                multipleChoicePanel.setVisible(true);
            }
            case TRUE_FALSE -> {
                questionLabel.setVisible(true);
                scoreLabel.setVisible(true);
                categoryLabel.setVisible(true);
                submitButton.setVisible(true);

                buildTrueFalsePanel(state.getChoices());
                trueFalsePanel.setVisible(true);
            }
            case CORRECT -> {
                resultLabel.setVisible(true);
                resultLabel.setText("Correct!");
                resultLabel.setForeground(Color.GREEN.darker());
                nextButton.setVisible(true);

                questionLabel.setVisible(true);
                scoreLabel.setVisible(true);
                categoryLabel.setVisible(true);
            }
            case INCORRECT -> {
                resultLabel.setVisible(true);
                resultLabel.setText("Incorrect!");
                resultLabel.setForeground(Color.RED);
                answerLabel.setVisible(true);
                answerLabel.setText("Answer: " + state.getAnswer());
                nextButton.setVisible(true);

                questionLabel.setVisible(true);
                scoreLabel.setVisible(true);
                categoryLabel.setVisible(true);
            }
            case QUIZ_OVER -> {
                buildQuizFinishedPanel(state.getCumulativeScore());
                add(quizFinishedPanel);
                quizFinishedPanel.setVisible(true);
                submitButton.setVisible(false);
                submitButton.setEnabled(false);
                scoreLabel.setVisible(false);
                categoryLabel.setVisible(false);
            }
        }

        revalidate();
        repaint();
    }

    private void styleLabel(JLabel label, int size, boolean bold) {
        label.setFont(new Font("Algerian", bold ? Font.BOLD : Font.PLAIN, size));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    private void styleRadioButton(JRadioButton radioButton) {
        radioButton.setFont(new Font("Algerian", Font.PLAIN, 18));
        radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel createCenteredPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(component);
        return panel;
    }

    public String getViewName() {
        return viewName;
    }

    // use this in AppBuilder to add instructions button!
    public void addInstructionsAction(Runnable action) {
        instructionsButton.addActionListener(e -> action.run());
    }
}
