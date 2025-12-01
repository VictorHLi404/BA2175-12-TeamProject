package view;

import interfaceadapter.main_menu.MainMenuController;
import interfaceadapter.main_menu.MainMenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Main Menu";
    private final MainMenuViewModel mainMenuViewModel;
    private MainMenuController mainMenuController;
    private JButton playButton;
    private JButton createQuizButton;
    private JButton viewScoresButton;
    private JButton instructionsButton;


    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        mainMenuViewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // padding

        // -------- TITLE --------
        JLabel title = new JLabel(MainMenuViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(new Font("Algerian", Font.BOLD, 64));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // -------- BUTTON PANEL --------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        playButton = new JButton(MainMenuViewModel.PLAY_BUTTON_LABEL);
        createQuizButton = new JButton("CREATE QUIZ");
        viewScoresButton = new JButton("VIEW MY SCORES");
        instructionsButton = new JButton("INSTRUCTIONS");

        styleButton(playButton, 400, 80);
        styleButton(createQuizButton, 400, 80);
        styleButton(viewScoresButton, 400, 80);
        styleButton(instructionsButton, 300, 60);

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(createQuizButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(viewScoresButton);
        buttonPanel.add(Box.createVerticalStrut(40));
        buttonPanel.add(instructionsButton);

        add(buttonPanel, BorderLayout.CENTER);

        playButton.addActionListener(e -> {
            if (mainMenuController != null) {
                mainMenuController.switchToQuizCustomizationView();
            }
        });

        createQuizButton.addActionListener (new ActionListener() {

            public void actionPerformed (ActionEvent e) {
                if (e.getSource().equals(createQuizButton)) {
                    mainMenuController.switchToCreateQuizView();
                }
                
            }

        });

// 给 instructions 按钮加监听
        instructionsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    InstructionsContent.getInstructions(),
                    "Instructions",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        viewScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                if (e.getSource().equals(viewScoresButton)){
                    mainMenuController.switchToViewScore();
                }
            }

        });
    }


    private void styleButton(JButton button, int width, int height) {
        button.setFocusPainted(false);
        button.setFont(new Font("Algerian", Font.PLAIN, 24));
        button.setBackground(new Color(230, 230, 230));
        button.setMaximumSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void addPlayAction(Runnable action) {
        playButton.addActionListener(e -> action.run());
    }
}
