import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CompetitionGUI extends JFrame {
    private CompetitorList competitorList;
    private JTextArea competitorsTextArea;

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

        JButton generateReportButton = new JButton("Generate Competition Report");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCompetitionReport();
            }
        });

        competitorsTextArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(competitorsTextArea);

        JPanel panel = new JPanel();
        panel.add(viewCompetitorsButton);
        panel.add(generateReportButton);
        panel.add(scrollPane);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void displayCompetitors() {
        // Fetch competitors from CompetitorList
        StringBuilder competitorsInfo = new StringBuilder();
        for (Competitor competitor : competitorList.getCompetitors()) {
            competitorsInfo.append("Competitor Number: ").append(competitor.getCompetitorNumber()).append("\n");
            competitorsInfo.append("Name: ").append(competitor.getName().getFullName()).append("\n");
            competitorsInfo.append("Email: ").append(competitor.getEmail()).append("\n");
            competitorsInfo.append("Date of Birth: ").append(competitor.getDateOfBirth()).append("\n");
            competitorsInfo.append("Category: ").append(competitor.getCategory()).append("\n");
            competitorsInfo.append("Scores: ").append(Arrays.toString(competitor.getScores())).append("\n");
            competitorsInfo.append("\n");
        }

        // Display competitors in JTextArea
        competitorsTextArea.setText(competitorsInfo.toString());
    }

    private void generateCompetitionReport() {
        // Create an instance of the Result class
        Result result = new Result(competitorList.getCompetitors());

        // Use the writeReportToTextArea method to display the report in the JTextArea
        result.writeReportToTextArea(competitorsTextArea);
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
