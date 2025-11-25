package view;

import entities.QuizResults;
import interface_adapter.compare_score.CompareScoreController;
import interface_adapter.compare_score.CompareScoreState;
import interface_adapter.compare_score.CompareScoreViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareScoreView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "compare score";
    private final CompareScoreViewModel compareScoreViewModel;

    private final JTextField quizNameField = new JTextField(15);

    private JTable resultsTable;
    private JLabel title;

    private CompareScoreController compareScoreController = null;

    public CompareScoreView(CompareScoreViewModel compareScoreViewModel) {
        this.compareScoreViewModel = compareScoreViewModel;
        this.compareScoreViewModel.addPropertyChangeListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        CompareScoreState  compareScoreState = compareScoreViewModel.getState();
        title = new JLabel(compareScoreState.getQuizNameOrDefault());
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        resultsTable = createResultsTable(compareScoreState);
        add(new JScrollPane(resultsTable));
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CompareScoreState compareScoreState = compareScoreViewModel.getState();
        removeAll();
        resultsTable = createResultsTable(compareScoreState);
        title =  new JLabel(compareScoreState.getQuizNameOrDefault());
        add(title);
        add(new JScrollPane(resultsTable));
        revalidate();
        repaint();
    }

    private JTable createResultsTable(CompareScoreState compareScoreState) {
        List<List<String>> normalizedResults = compareScoreState.getNormalizedQuizResults();
        String[] columnNames = {"Username", "Score"};

        Object[][] data = new Object[normalizedResults.size()][columnNames.length];

        for (int i = 0; i < normalizedResults.size(); i++) {
            List<String> normalizedResult = normalizedResults.get(i);
            data[i][0] = normalizedResult.get(0);
            data[i][1] = normalizedResult.get(1);
        }

        // non-editable table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // all cells non-editable
            }
        };

        return new JTable(model);
    }
    public CompareScoreController getCompareScoreController() {
        return compareScoreController;
    }

    public void setCompareScoreController(CompareScoreController compareScoreController) {
        this.compareScoreController = compareScoreController;
    }
}
