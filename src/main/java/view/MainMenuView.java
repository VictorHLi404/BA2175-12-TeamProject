package view;

import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuViewModel;

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

    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.GRAY);
        mainMenuViewModel.addPropertyChangeListener(this);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleCard = new JLabel(MainMenuViewModel.TITLE_LABEL);
        titleCard.setFont(new Font("Algerian", Font.BOLD, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NORTH;
        this.add(titleCard, gbc);

        JButton play = new JButton(MainMenuViewModel.PLAY_BUTTON_LABEL);
        play.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        play.setPreferredSize(new Dimension(400, 150));
        play.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        this.add(play, gbc);

        JButton createQuiz = new JButton("CREATE QUIZ");
        createQuiz.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        createQuiz.setPreferredSize(new Dimension(400, 150));
        createQuiz.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1;
        this.add(createQuiz, gbc);

        createQuiz.addActionListener (new ActionListener() {

            public void actionPerformed (ActionEvent e) {
                System.out.println("CreateQuiz button is clicked"); // This will be changed.
            }

        });

        JButton viewScores = new JButton("VIEW MY SCORES");
        viewScores.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        viewScores.setPreferredSize(new Dimension(400, 150));
        viewScores.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1;
        this.add(viewScores, gbc);

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
}
