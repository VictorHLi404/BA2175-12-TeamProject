// Only included for testing
// Delete this before merging with main branch

package interface_adapter.UI;

import use_case.create_quiz.QuestionInputData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class dummyUI extends JFrame {

    private List<QuestionInputData> questionList = new ArrayList<>();

    private final JLabel messageLabel = new JLabel("");

    private final JTextField quizNameField = new JTextField(15);

    private final JComboBox<String> categoryMenu = new JComboBox<>(new String[]{
            "General Knowledge", "Entertainment: Books", "Entertainment: Film", "Entertainment: Music",
            "Entertainment: Musicals & Theatres", "Entertainment: Television", "Entertainment: Video Games",
            "Entertainment: Board Games", "Science & Nature", "Science: Computers", "Science: Mathematics",
            "Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities", "Animals",
            "Vehicles", "Entertainment: Comics", "Science: Gadgets", "Entertainment: Japanese Anime & Manga",
            "Entertainment: Cartoon & Animations"
    });

    private final JComboBox<String> difficultyMenu = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
    private final JComboBox<String> questionTypeMenu = new JComboBox<>(new String[]{"Multiple Choice", "True/False"});

    private final JTextArea questionArea = new JTextArea(3, 25);

    private final JTextField optionAField = new JTextField(20);
    private final JTextField optionBField = new JTextField(20);
    private final JTextField optionCField = new JTextField(20);
    private final JTextField optionDField = new JTextField(20);

    private final JRadioButton A_Button = new JRadioButton("A");
    private final JRadioButton B_Button = new JRadioButton("B");
    private final JRadioButton C_Button = new JRadioButton("C");
    private final JRadioButton D_Button = new JRadioButton("D");

    private final JButton addQuestionButton = new JButton("Add Question");
    private final JButton saveQuizButton = new JButton("Save Quiz");

    public dummyUI() {
        super("Create a new Quiz");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 750);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Message label
        mainPanel.add(messageLabel);

        mainPanel.add(new JLabel("Quiz Name:"));
        mainPanel.add(quizNameField);

        mainPanel.add(new JLabel("Category:"));
        mainPanel.add(categoryMenu);

        mainPanel.add(new JLabel("Difficulty:"));
        mainPanel.add(difficultyMenu);

        mainPanel.add(new JLabel("Type:"));
        mainPanel.add(questionTypeMenu);

        mainPanel.add(new JLabel("Question:"));
        mainPanel.add(new JScrollPane(questionArea));

        JLabel optionALabel = new JLabel("Option A:");
        mainPanel.add(optionALabel);
        mainPanel.add(optionAField);

        JLabel optionBLabel = new JLabel("Option B:");
        mainPanel.add(optionBLabel);
        mainPanel.add(optionBField);

        JLabel optionCLabel = new JLabel("Option C:");
        mainPanel.add(optionCLabel);
        mainPanel.add(optionCField);

        JLabel optionDLabel = new JLabel("Option D:");
        mainPanel.add(optionDLabel);
        mainPanel.add(optionDField);

        // Correct answer radio buttons
        mainPanel.add(new JLabel("Correct Answer:"));

        ButtonGroup optionButtons = new ButtonGroup();
        optionButtons.add(A_Button);
        optionButtons.add(B_Button);
        optionButtons.add(C_Button);
        optionButtons.add(D_Button);

        A_Button.setActionCommand("A");
        B_Button.setActionCommand("B");
        C_Button.setActionCommand("C");
        D_Button.setActionCommand("D");

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(A_Button);
        optionsPanel.add(B_Button);
        optionsPanel.add(C_Button);
        optionsPanel.add(D_Button);

        mainPanel.add(optionsPanel);

        mainPanel.add(addQuestionButton);
        mainPanel.add(saveQuizButton);

        add(mainPanel, BorderLayout.CENTER);

        questionTypeMenu.addActionListener(e ->
                {
                    if ("True/False".equals(questionTypeMenu.getSelectedItem().toString())) {

                        optionALabel.setText("");
                        optionBLabel.setText("");
                        optionCLabel.setText("");
                        optionDLabel.setText("");

                        optionAField.setEditable(false);
                        optionBField.setEditable(false);
                        optionCField.setEditable(false);
                        optionDField.setEditable(false);

                        A_Button.setText("True");
                        B_Button.setText("False");
                        C_Button.setText("");
                        D_Button.setText("");
                        C_Button.setEnabled(false);
                        D_Button.setEnabled(false);

                    }
                    else {

                        optionALabel.setText("Option A:");
                        optionBLabel.setText("Option B:");
                        optionCLabel.setText("Option C:");
                        optionDLabel.setText("Option D:");

                        optionAField.setEditable(true);
                        optionBField.setEditable(true);
                        optionCField.setEditable(true);
                        optionDField.setEditable(true);

                        A_Button.setText("A");
                        B_Button.setText("B");
                        C_Button.setText("C");
                        D_Button.setText("D");
                        C_Button.setEnabled(true);
                        D_Button.setEnabled(true);

                    }

                }
        );

        // Add question action
        addQuestionButton.addActionListener(e -> {

            String category = categoryMenu.getSelectedItem().toString();
            String difficulty = difficultyMenu.getSelectedItem().toString();
            String format = questionTypeMenu.getSelectedItem().toString();
            String question = questionArea.getText();

            List<String> options = new ArrayList<>();
            String optionA = optionAField.getText();
            options.add(optionA);
            String optionB = optionAField.getText();
            options.add(optionB);
            String optionC = optionAField.getText();
            options.add(optionC);
            String optionD = optionAField.getText();
            options.add(optionD);

            if (question.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "The question can't be empty!");
                return;
            } else if (optionA.trim().isEmpty() || optionB.trim().isEmpty() || optionC.trim().isEmpty()
                    || optionD.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all four options!");
                return;
            } else if (optionButtons.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "Please select the correct answer!");
                return;
            }

            String correctOption = optionButtons.getSelection().getActionCommand();


            QuestionInputData questionData = new QuestionInputData(
                    category, difficulty, format, question, options, correctOption
            );

            questionList.add(questionData);
            messageLabel.setText("Question added! Total: " + questionList.size());
            clearQuestionForm();
        });

        // Save quiz action — prints to the console
        saveQuizButton.addActionListener(e -> {
            String quizName = quizNameField.getText();
            System.out.println("=== QUIZ SAVED ===");
            System.out.println("Quiz name: " + quizName);
            System.out.println("Total questions: " + questionList.size());

            for (QuestionInputData q : questionList) {
                System.out.println("Question: " + q.getQuestion());
                System.out.println("Correct: " + q.getCorrectChoice());
                System.out.println("---");
            }

            messageLabel.setText("Quiz saved! Check console output.");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            dummyUI quizScreen = new dummyUI();
            quizScreen.setVisible(true);
        });
    }

    private void clearQuestionForm() {
        questionArea.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");

        A_Button.setSelected(false);
        B_Button.setSelected(false);
        C_Button.setSelected(false);
        D_Button.setSelected(false);
    }
}
