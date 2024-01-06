import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompetitionGUI extends JFrame {
    private CompetitorList competitorList;

    public CompetitionGUI(CompetitorList competitorList) {
        this.competitorList = competitorList;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Competition GUI");

        JButton viewCompetitorsButton = new JButton("View Competitors");
        viewCompetitorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCompetitors();
            }
        });

        JPanel panel = new JPanel();
        panel.add(viewCompetitorsButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void displayCompetitors() {
        competitorList.generateCompetitorList(); // Using the existing method
    }

    public void launchGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }
}
