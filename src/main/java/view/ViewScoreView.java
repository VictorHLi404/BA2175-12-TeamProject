package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.compare_score.CompareScoreController;
import interface_adapter.view_score.ViewScoreController;
import interface_adapter.view_score.ViewScoreState;
import interface_adapter.view_score.ViewScoreViewModel;
import use_case.view_score.PerQuizResultData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

public class ViewScoreView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "view Score";
    private final ViewScoreViewModel viewScoreViewModel;
    private final ViewManagerModel viewManagerModel;
    private ViewScoreController viewScoreController;
    private CompareScoreController compareScoreController;

    private final JButton viewScoreButton;
    private final JButton backButton;
    private JButton instructionsButton;

    private final JLabel scoreLabel;
    private final JLabel messageLabel;

    private DefaultTableModel historyTableModel;
    private JTable historyTable;
    private JPanel tablePanel;



    public ViewScoreView(ViewScoreViewModel viewScoreViewModel, ViewManagerModel viewManagerModel) {
        this.viewScoreViewModel = viewScoreViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewScoreViewModel.addPropertyChangeListener(this);

        // ===== MAIN PANEL =====
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // ===== TITLE =====
        JLabel titleLabel = new JLabel("View Score", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 48));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(30));

        // ===== SCORE LABEL =====
        scoreLabel = new JLabel("Score: --");
        scoreLabel.setFont(new Font("Algerian", Font.BOLD, 36));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        scoreLabel.setVisible(false); // hidden initially
        add(scoreLabel);
        add(Box.createVerticalStrut(20));
        // ===== MESSAGE =====
        messageLabel = new JLabel("Select a user to view score.");
        messageLabel.setFont(new Font("Algerian", Font.BOLD, 28));
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(messageLabel);
        add(Box.createVerticalStrut(20));

        // ===== VIEW SCORE BUTTON =====
        viewScoreButton = new JButton("View Score");
        styleButton(viewScoreButton, 250, 80);
        add(viewScoreButton);
        add(Box.createVerticalStrut(20));

        // ===== TABLE PANEL =====
        historyTableModel = new DefaultTableModel(new Object[]{"Quiz Name", "Score", "Compare"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Algerian", Font.PLAIN, 10));
        historyTable.setRowHeight(28);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setFont(new Font("Algerian", Font.PLAIN, 10));
        historyTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        historyTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        historyTable.getColumn("Compare").setCellRenderer(new ButtonRenderer());
        historyTable.getColumn("Compare").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setVisible(false);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.add(scrollPane);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Score History"));
        add(tablePanel);
        add(Box.createVerticalStrut(20));

        // ===== BOTTOM BUTTONS =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        backButton = new JButton("Back");
        styleButton(backButton, 180, 60);
        buttonPanel.add(backButton);

        instructionsButton = new JButton("Instructions");
        styleButton(instructionsButton, 200, 60);
        buttonPanel.add(instructionsButton);

        add(buttonPanel);

        // ActionListener
        viewScoreButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(viewScoreButton) && viewScoreController != null){
                            final ViewScoreState currentState = viewScoreViewModel.getState();
                            final String targetUsername = currentState.getUsername();
                            // System.out.println(targetUsername);
                            if (targetUsername != null && !targetUsername.isEmpty()) {
                                viewScoreController.execute(targetUsername);
                            } else {
                                //System.out.println("Username is empty");
                                messageLabel.setText("Unable to load scores: no user is logged in.");
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
                            resetView();
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
        instructionsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    InstructionsContent.getInstructions(),
                    "Instructions",
                    JOptionPane.INFORMATION_MESSAGE
            );
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
    private void handleCompareClick(int row) {
        PerQuizResultData selectedQuiz = viewScoreViewModel.getState().getPerQuizResultData().get(row);
        UUID quizResultsId = selectedQuiz.getQuizResultId();

        if (compareScoreController != null) {
            compareScoreController.executeQuizResultsId(quizResultsId);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ViewScoreState state = viewScoreViewModel.getState();
        this.messageLabel.setText(state.getViewMessage());

        historyTableModel.setRowCount(0); // Clear old rows

        int i = 1;
        for (PerQuizResultData p : state.getPerQuizResultData()) {
            historyTableModel.addRow(new Object[] {
                    p.getQuizName(),
                    p.getCorrect() + "/" + p.getTotal(),
                    "Compare"
            });
            i++;
        }
        if (!state.getPerQuizResultData().isEmpty()) {
            tablePanel.setVisible(true);
            viewScoreButton.setVisible(false);
        }

        this.revalidate();
        this.repaint();
    }

    private void resetView() {
        historyTableModel.setRowCount(0);
        tablePanel.setVisible(false);
        viewScoreButton.setVisible(true);
        messageLabel.setText("Select a user to view score.");
    }

    public void setViewScoreController(ViewScoreController viewScoreController) {
        this.viewScoreController = viewScoreController;
    }

    public void setCompareScoreController(CompareScoreController compareScoreController) {
        this.compareScoreController = compareScoreController;
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.setFont(new Font("Algerian", Font.PLAIN, 18));
            button.setBackground(new Color(230, 230, 230));
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            button.setText("Compare");
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                handleCompareClick(row);
            }
            clicked = false;
            return "Compare";
        }
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Algerian", Font.PLAIN, 18));
            setBackground(new Color(230, 230, 230));
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            setText("Compare");
            return this;
        }
    }


}
