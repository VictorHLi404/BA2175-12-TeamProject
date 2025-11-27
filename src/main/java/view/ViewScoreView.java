package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_score.ViewScoreController;
import interface_adapter.view_score.ViewScoreState;
import interface_adapter.view_score.ViewScoreViewModel;
import use_case.view_score.PerQuizResultData;

import javax.swing.*;
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

    private final JButton viewScoreButton;
    private final JButton backButton;

    private final JLabel scoreDisplayLabel = new JLabel("Score: --");
    private final JLabel messageDisplayLabel = new JLabel("Select a user to view score.");

    private DefaultTableModel historyTableModel;
    private JTable historyTable;
    private JPanel tablePanel;



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
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(messageDisplayLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(viewScoreButton);

// === BOTTOM-LEFT (SOUTH): Back Button ===
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        backButton.setPreferredSize(new Dimension(180, 60));
        backButton.setBackground(Color.GREEN);

        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
// for score table
        historyTableModel = new DefaultTableModel(new Object[]{"Quiz #", "Score", "Compare"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        historyTable = new JTable(historyTableModel);

        historyTable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        historyTable.setRowHeight(28);

        historyTable.getColumn("Compare").setCellRenderer(new ButtonRenderer());
        historyTable.getColumn("Compare").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(2500, 200));

        tablePanel = new JPanel();
        tablePanel.setVisible(false);
        tablePanel.setOpaque(false);
        tablePanel.add(scrollPane);
        JPanel bottomWrapper = new JPanel(new BorderLayout());
        bottomWrapper.setOpaque(false);

        bottomWrapper.add(tablePanel, BorderLayout.CENTER);  // table

        centerPanel.add(Box.createVerticalStrut(10));   // spacing
        centerPanel.add(bottomWrapper);
        this.add(centerPanel, BorderLayout.CENTER);

        tablePanel.setBorder(BorderFactory.createTitledBorder("Score History"));
        bottomWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(300, 250));

        // ActionListener
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
    private void handleCompareClick(int row) {
        PerQuizResultData selectedQuiz = viewScoreViewModel.getState().getPerQuizResultData().get(row);
        UUID quizResultsId = selectedQuiz.getQuizResultId();

        System.out.println("DEBUG: Selected QuizResultId = " + quizResultsId);

        if (viewScoreController != null) {
            viewScoreController.switchToCompareView(quizResultsId);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ViewScoreState state = viewScoreViewModel.getState();
        this.messageDisplayLabel.setText(state.getViewMessage());

        historyTableModel.setRowCount(0); // Clear old rows

        int i = 1;
        for (PerQuizResultData p : state.getPerQuizResultData()) {
            historyTableModel.addRow(new Object[] {
                    "Quiz " + i,
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

    public void setViewScoreController(ViewScoreController viewScoreController) {
        this.viewScoreController = viewScoreController;
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

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
