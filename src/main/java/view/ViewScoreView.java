package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_score.ViewScoreController;
import interface_adapter.view_score.ViewScoreState;
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
    private final ViewManagerModel viewManagerModel;
    private ViewScoreController viewScoreController;

    private final JButton viewScoreButton;
    private final JButton backButton;

    private final JLabel scoreDisplayLabel = new JLabel("Score: --");
    private final JLabel messageDisplayLabel = new JLabel("Select a user to view score.");

    public ViewScoreView(ViewScoreViewModel viewScoreViewModel, ViewManagerModel viewManagerModel) {
        this.viewScoreViewModel = viewScoreViewModel;
        this.viewManagerModel = viewManagerModel;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.GRAY);
        this.viewScoreViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

// === TOP (NORTH): Title ===
        JLabel titleCard = new JLabel("View Score", SwingConstants.CENTER);
        titleCard.setFont(new Font("Algerian", Font.BOLD, 48));
        titleCard.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        this.add(titleCard, BorderLayout.NORTH);

// === CENTER AREA ===
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

// Message label
        messageDisplayLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        messageDisplayLabel.setForeground(new Color(255, 255, 200));
        messageDisplayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

// Score label (hidden initially)
        scoreDisplayLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        scoreDisplayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreDisplayLabel.setVisible(false);

// View Score Button
        viewScoreButton = new JButton("View Score");
        viewScoreButton.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        viewScoreButton.setPreferredSize(new Dimension(240, 80));
        viewScoreButton.setMaximumSize(new Dimension(240, 80));
        viewScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewScoreButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        viewScoreButton.setBackground(Color.GREEN);

// Add to center panel with spacing
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(messageDisplayLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scoreDisplayLabel);
        centerPanel.add(Box.createVerticalStrut(120));
        centerPanel.add(viewScoreButton);
        centerPanel.add(Box.createVerticalGlue());

        this.add(centerPanel, BorderLayout.CENTER);

// === BOTTOM-LEFT (SOUTH): Back Button ===
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        backButton.setPreferredSize(new Dimension(180, 60));
        backButton.setBackground(Color.GREEN);

        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        viewScoreButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(viewScoreButton) && viewScoreController != null){
                            final ViewScoreState currentState = viewScoreViewModel.getState();

                            if (viewScoreController != null) {
                                String targetUsername = viewScoreViewModel.getState().getUsername();
                                //System.out.println("DEBUG: Viewing score for: " + targetUsername);
                                viewScoreController.execute(targetUsername);
                            } else {
                                //System.out.println("Username is empty");
                            }
                        }
                    }
                }
        );

        backButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(backButton)) {
                            if (viewScoreController == null) {
                                //System.out.println("DEBUG ERROR: ViewScoreController is NULL in View.");
                            } else {
                                //System.out.println("DEBUG SUCCESS: ViewScoreController is NOT NULL. Switching views...");
                                viewScoreController.switchToMainMenuView();
                            }
                        }
                    }
                }
        );

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ViewScoreState state = viewScoreViewModel.getState();
        this.messageDisplayLabel.setText(state.getViewMessage());

        if (state.getScore() > 0 || state.getViewMessage().startsWith("Score:")) {
            this.scoreDisplayLabel.setText("Total Score: " + state.getScore() + "%");
            this.scoreDisplayLabel.setVisible(true);
        } else {
            this.scoreDisplayLabel.setText("");
        }

        this.revalidate();
        this.repaint();
    }

    public void setViewScoreController(ViewScoreController viewScoreController) {
        this.viewScoreController = viewScoreController;
    }


}
