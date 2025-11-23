package view;

import interface_adapter.view_score.ViewScoreController;
import interface_adapter.view_score.ViewScoreViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewScoreView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "view Score";
    private final ViewScoreViewModel viewScoreViewModel;
    private ViewScoreController viewScoreController;

    public ViewScoreView(ViewScoreViewModel viewScoreViewModel) {
        this.viewScoreViewModel = viewScoreViewModel;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.GRAY);
        this.viewScoreViewModel.addPropertyChangeListener(this);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleCard = new JLabel("View Score");
        titleCard.setFont(new Font("Algerian", Font.BOLD, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NORTH;
        this.add(titleCard, gbc);

        JButton selectUserButton = new JButton(viewScoreViewModel.SELECT_USER_LABEL);
        selectUserButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        selectUserButton.setPreferredSize(new Dimension(400, 150));
        selectUserButton.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        this.add(selectUserButton, gbc);

        JButton viewScoreButton = new JButton(viewScoreViewModel.VIEW_SCORE_LABEL);
        viewScoreButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        viewScoreButton.setPreferredSize(new Dimension(400, 150));
        viewScoreButton.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1;
        this.add(viewScoreButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        backButton.setPreferredSize(new Dimension(400, 150));
        backButton.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1;
        this.add(backButton, gbc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
