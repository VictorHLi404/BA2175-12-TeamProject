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
    private final JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

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

        // Center panel for question, choices, and submit/next buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // move under options

        nextButton.setVisible(false); // hidden until answer is submitted

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalStrut(10));

// Add dynamic panels (choices) first
        centerPanel.add(multipleChoicePanel);
        centerPanel.add(trueFalsePanel);
        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(submitButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nextButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(instructionsButton); // instructions below submit/next

        add(centerPanel, BorderLayout.CENTER);

        // Listen to ViewModel changes
        viewModel.addPropertyChangeListener(evt -> refreshView());

        // Submit button action
        submitButton.addActionListener(e -> onSubmit());

        // next button action
        nextButton.addActionListener(e -> onNext());

        refreshView();
    }

    private void onNext() {
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
        JButton mainMenuBtn = new JButton("Back to Main Menu");
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

        multipleChoicePanel.setVisible(true);
        trueFalsePanel.setVisible(true);
        nextButton.setVisible(false);
        nextButton.setEnabled(true);

        resultLabel.setVisible(false);
        resultLabel.setText("");

        questionLabel.setText("<html><h3>" + state.getQuestionText() + "</h3></html>");
        scoreLabel.setText("Score: " + state.getCumulativeScore());

        if (state.getQuestionText() != null && !state.getQuestionText().isEmpty()) {
            Boolean correct = state.isLastAnswerCorrect();
            if (correct != null) {
                resultLabel.setVisible(true);
                if (correct) {
                    resultLabel.setText("Correct!");
                    resultLabel.setForeground(Color.GREEN.darker());
                } else {
                    resultLabel.setText("Incorrect!");
                    resultLabel.setForeground(Color.RED);
                }
            } else {
                resultLabel.setText(""); // no answer submitted yet
            }
        } else {
            resultLabel.setText("");
        }

        // Remove previous dynamic panels
        remove(multipleChoicePanel);
        remove(trueFalsePanel);
        remove(quizFinishedPanel);

        // Add panel based on state
        if (state.isFinished()) {
            buildQuizFinishedPanel(state.getCumulativeScore());

            // Hide all other components
            questionLabel.setVisible(false);
            scoreLabel.setVisible(false);
            resultLabel.setVisible(false);
            multipleChoicePanel.setVisible(false);
            trueFalsePanel.setVisible(false);

            add(quizFinishedPanel);
            submitButton.setEnabled(false);
            submitButton.setVisible(false);
            nextButton.setEnabled(false);
            nextButton.setVisible(false);
        } else if ("multiple".equalsIgnoreCase(state.getQuestionFormat())) {
            buildMultipleChoicePanel(state.getChoices());
            add(multipleChoicePanel);
            submitButton.setEnabled(true);
        } else if ("boolean".equalsIgnoreCase(state.getQuestionFormat())) {
            buildTrueFalsePanel(state.getChoices());
            add(trueFalsePanel);
            submitButton.setEnabled(true);
        }

        revalidate();
        repaint();
    }

    public String getViewName() {
        return viewName;
    }

    // use this in AppBuilder to add instructions button!
    public void addInstructionsAction(Runnable action) {
        instructionsButton.addActionListener(e -> action.run());
    }
}
