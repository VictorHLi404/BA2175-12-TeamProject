package view;

import entities.QuizResults;
import interface_adapter.compare_score.CompareScoreController;
import interface_adapter.compare_score.CompareScoreState;
import interface_adapter.compare_score.CompareScoreViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CompareScoreView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "compare score";
    private final CompareScoreViewModel compareScoreViewModel;
    private CompareScoreController compareScoreController = null;

    private JLabel titleLabel;
    private JTable resultsTable;
    private JPanel tablePanel;
    private JButton backButton;

    public CompareScoreView(CompareScoreViewModel compareScoreViewModel) {
        this.compareScoreViewModel = compareScoreViewModel;
        this.compareScoreViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        rebuildView(compareScoreViewModel.getState());
    }

    private void rebuildView(CompareScoreState state) {
        removeAll();

        // ===== TITLE =====
        titleLabel = new JLabel(state.getQuizNameOrDefault(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 48));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(30));

        // ===== TABLE =====
        resultsTable = createResultsTable(state);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);

        tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.add(scrollPane);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Results"));
        add(tablePanel);
        add(Box.createVerticalStrut(20));

        // ===== BACK BUTTON =====
        backButton = new JButton("Back");
        styleButton(backButton, 250, 80);
        backButton.addActionListener(e -> {
            if (compareScoreController != null) {
                compareScoreController.switchToUserScoreView();
            }
        });
        add(backButton);
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        revalidate();
        repaint();
    }

    private JTable createResultsTable(CompareScoreState state) {
        List<List<String>> results = state.getNormalizedQuizResults();
        String[] columnNames = {"Username", "Score"};
        Object[][] data = new Object[results.size()][columnNames.length];

        for (int i = 0; i < results.size(); i++) {
            List<String> row = results.get(i);
            data[i][0] = row.get(0);
            data[i][1] = row.get(1);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        table.setRowHeight(36);

        // Center text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        return table;
    }

    private void styleButton(JButton button, int width, int height) {
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        button.setBackground(new Color(230, 230, 230));
        button.setMaximumSize(new Dimension(width, height));
        button.setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        rebuildView(compareScoreViewModel.getState());
    }

    public String getViewName() {
        return viewName;
    }

    public void setCompareScoreController(CompareScoreController compareScoreController) {
        this.compareScoreController = compareScoreController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
