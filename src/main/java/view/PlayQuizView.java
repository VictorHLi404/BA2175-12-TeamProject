package view;

import interface_adapter.play.PlayQuizController;
import interface_adapter.play.PlayQuizState;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A single UI for playing quizzes that adapts based on question type and answer correctness.
 */
public class PlayQuizView extends JPanel {

    private final PlayQuizState state;
    private final PlayQuizController controller;

    // Labels
    private final JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

    // Panels for each view
    private final JPanel multipleChoicePanel = new JPanel();
    private final JPanel trueFalsePanel = new JPanel();
    private final JPanel quizFinishedPanel = new JPanel();

    // Submit button
    private final JButton submitButton = new JButton("Submit");

    // Currently active button group
    private ButtonGroup buttonGroup;
    private PlayQuizController playQuizController;

    public PlayQuizView(PlayQuizState state, PlayQuizController controller) {
        this.state = state;
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.addActionListener(e -> onSubmit());

        // Add fixed labels
        add(questionLabel);
        add(scoreLabel);
        add(resultLabel);
        add(submitButton);

        refreshView();
    }

    private void onSubmit() {
        if (buttonGroup == null || buttonGroup.getSelection() == null) return;
        String selectedChoice = buttonGroup.getSelection().getActionCommand();

        // You need a way to track current question index and previous answers
        int currentIndex = state.getCumulativeScore(); // simple placeholder, adjust in real app
        controller.execute(null, currentIndex, selectedChoice, List.of()); // replace null with quizId
        refreshView();
    }

    private void buildMultipleChoicePanel() {
        multipleChoicePanel.removeAll();
        buttonGroup = new ButtonGroup();
        List<String> choices = state.getChoices();
        if (choices != null) {
            multipleChoicePanel.setLayout(new GridLayout(choices.size(), 1, 5, 5));
            for (String choice : choices) {
                JRadioButton btn = new JRadioButton(choice);
                btn.setActionCommand(choice);
                buttonGroup.add(btn);
                multipleChoicePanel.add(btn);
            }
        }
    }

    private void buildTrueFalsePanel() {
        trueFalsePanel.removeAll();
        buttonGroup = new ButtonGroup();
        List<String> choices = state.getChoices(); // usually ["True", "False"]
        if (choices != null) {
            trueFalsePanel.setLayout(new GridLayout(choices.size(), 1, 5, 5));
            for (String choice : choices) {
                JRadioButton btn = new JRadioButton(choice);
                btn.setActionCommand(choice);
                buttonGroup.add(btn);
                trueFalsePanel.add(btn);
            }
        }
    }

    private void buildQuizFinishedPanel() {
        quizFinishedPanel.removeAll();
        quizFinishedPanel.setLayout(new BoxLayout(quizFinishedPanel, BoxLayout.Y_AXIS));
        JLabel finishedLabel = new JLabel("Quiz Over! Final score: " + state.getCumulativeScore(), SwingConstants.CENTER);
        JButton mainMenuBtn = new JButton("Back to Main Menu");
        mainMenuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuBtn.addActionListener(e -> System.out.println("Go to main menu")); // hook into navigation
        finishedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizFinishedPanel.add(Box.createVerticalStrut(20));
        quizFinishedPanel.add(finishedLabel);
        quizFinishedPanel.add(Box.createVerticalStrut(10));
        quizFinishedPanel.add(mainMenuBtn);
    }

    public void refreshView() {
        // Update labels
        questionLabel.setText("<html><h3>" + state.getQuestionText() + "</h3></html>");
        scoreLabel.setText("Score: " + state.getCumulativeScore());
        resultLabel.setText("");
        if (state.getQuestionText() != null && !state.getQuestionText().isEmpty()) {
            if (state.isLastAnswerCorrect()) {
                resultLabel.setText("Correct!");
                resultLabel.setForeground(Color.GREEN.darker());
            } else {
                resultLabel.setText("Incorrect!");
                resultLabel.setForeground(Color.RED);
            }
        }

        // Remove previous dynamic panels
        remove(multipleChoicePanel);
        remove(trueFalsePanel);
        remove(quizFinishedPanel);

        // Add panel based on state
        if (state.isFinished()) {
            buildQuizFinishedPanel();
            add(quizFinishedPanel);
            submitButton.setEnabled(false);
        } else if ("multiple choice".equalsIgnoreCase(state.getQuestionFormat())) {
            buildMultipleChoicePanel();
            add(multipleChoicePanel);
            submitButton.setEnabled(true);
        } else if ("true/false".equalsIgnoreCase(state.getQuestionFormat())) {
            buildTrueFalsePanel();
            add(trueFalsePanel);
            submitButton.setEnabled(true);
        }

        revalidate();
        repaint();
    }

    public void setPlayQuizController(PlayQuizController playQuizController) {
        this.playQuizController = playQuizController;
    }
}
