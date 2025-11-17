import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    public Main() {
        JFrame frame = new JFrame("Trivia Game");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.GRAY);
        frame.setContentPane(background);

        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleCard = new JLabel("Trivia Game");
        titleCard.setFont(new Font("Algerian", Font.BOLD, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.NORTH;
        background.add(titleCard, gbc);

        JButton play = new JButton("PLAY");
        play.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        play.setPreferredSize(new Dimension(400, 150));
        play.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        background.add(play, gbc);

        JButton createQuiz = new JButton("CREATE QUIZ");
        createQuiz.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        createQuiz.setPreferredSize(new Dimension(400, 150));
        createQuiz.setBackground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1;
        background.add(createQuiz, gbc);

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
        background.add(viewScores, gbc);

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new Main();
    }
}
