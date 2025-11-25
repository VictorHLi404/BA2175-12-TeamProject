package view;

import interface_adapter.compare_score.CompareScoreController;
import interface_adapter.compare_score.CompareScoreState;
import interface_adapter.compare_score.CompareScoreViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CompareScoreView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "compare score";
    private final CompareScoreViewModel compareScoreViewModel;

    private final JTextField quizNameField = new JTextField(15);

    private final JButton backButton;

    private CompareScoreController compareScoreController = null;

    public CompareScoreView(CompareScoreViewModel compareScoreViewModel) {
        this.compareScoreViewModel = compareScoreViewModel;
        this.compareScoreViewModel.addPropertyChangeListener(this);
        CompareScoreState  compareScoreState = compareScoreViewModel.getState();
        final JLabel title = new JLabel(compareScoreState.getQuizNameOrDefault());
        title.setAlignmentX(CENTER_ALIGNMENT);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
