import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PLANNER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        JLabel textLabel = new JLabel("이곳에 내용이 표시됩니다.", JLabel.CENTER);
        frame.add(textLabel, BorderLayout.CENTER);

        JButton homeButton = new JButton("HOME");
        JButton planButton = new JButton("PLAN");
        JButton editButton = new JButton("EDIT");

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textLabel.setText("1");
            }
        });

        planButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textLabel.setText("2");
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textLabel.setText("3");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(homeButton);
        buttonPanel.add(planButton);
        buttonPanel.add(editButton);
        frame.add(buttonPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }
}
