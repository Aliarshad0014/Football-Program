package controller;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import model.Competitor;
import controller.CompetitorList;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class StaffPanel extends JFrame {
    private CompetitorList competitorList;
    private JTextArea competitorsTextArea;

    public StaffPanel(CompetitorList competitorList) {
        this.competitorList = competitorList;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Staff Panel");

        Font buttonFont = new Font("Arial", Font.PLAIN, 12);

        JButton viewCompetitorsButton = createButton("View Competitors", buttonFont, e -> displayCompetitors());
        JButton sortCompetitorsByNumberButton = createButton("Sort Competitors by Number", buttonFont,
                e -> sortByCompetitorNumber());
        JButton sortCompetitorsAlphabeticallyButton = createButton("Sort Competitors Alphabetically", buttonFont,
                e -> sortByAlphabeticalOrder());
        JButton generateReportButton = createButton("Generate Full Report", buttonFont,
                e -> generateCompetitionReport());
        JButton viewScoresByIdButton = createButton("View Scores by ID", buttonFont, e -> displayScoresById());
        JButton viewShortDetails = createButton("View Short Details", buttonFont, e -> displayShortDetails());
        JButton viewFullDetails = createButton("View Full Details", buttonFont, e -> displayFullDetails());
        JButton recordScoresButton = createButton("Record Scores", buttonFont, e -> recordScoresDialog());
        JButton generateSummaryButton = createButton("Generate Summary Report", buttonFont,
                e -> displaySummaryReport());

        competitorsTextArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(competitorsTextArea);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(viewCompetitorsButton);
        panel.add(sortCompetitorsByNumberButton);
        panel.add(sortCompetitorsAlphabeticallyButton);
        panel.add(generateReportButton);
        panel.add(viewScoresByIdButton);
        panel.add(viewShortDetails);
        panel.add(viewFullDetails);
        panel.add(recordScoresButton);
        panel.add(generateSummaryButton);
        panel.add(Box.createVerticalGlue()); // Add vertical glue for alignment
        panel.add(scrollPane);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private JButton createButton(String text, Font font, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.addActionListener(listener);
        return button;
    }

    public void launchGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    private void displayCompetitors() {
        // Use the new method from CompetitorList to display competitors in a tabular
        // format
        competitorsTextArea.setText(competitorList.getCompetitorsInfoTable().toString());
    }

    private void sortByCompetitorNumber() {
        // Use the new sorting method in CompetitorList
        competitorList.sortCompetitorsByNumber();
        // Display sorted competitors in JTextArea
        displayCompetitors();
    }

    private void sortByAlphabeticalOrder() {
        // Use the new sorting method in CompetitorList
        competitorList.sortCompetitorsAlphabetically();
        // Display sorted competitors in JTextArea
        displayCompetitors();
    }

    private void generateCompetitionReport() {
        competitorList.writeReportToTextArea(competitorsTextArea);
    }

    private void displaySummaryReport() {
        competitorList.displaySummaryInGUI(competitorsTextArea);
    }

    private void displayScoresById() {
        String input = JOptionPane.showInputDialog("Enter Competitor ID:");
        try {
            int competitorID = Integer.parseInt(input);
            int[] scores = competitorList.getScoresById(competitorID);
            if (scores != null) {
                competitorsTextArea.setText(Arrays.toString(scores));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Competitor ID. Please enter a number.");
        }
    }

    private void displayShortDetails() {
        String input = JOptionPane.showInputDialog("Enter Competitor ID:");
        try {
            int competitorID = Integer.parseInt(input);
            competitorsTextArea.setText(competitorList.getCompetitorById(competitorID).getShortDetails());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Competitor ID. Please enter a number.");
        }
    }

    private void displayFullDetails() {
        String input = JOptionPane.showInputDialog("Enter Competitor ID:");
        try {
            int competitorID = Integer.parseInt(input);
            competitorsTextArea.setText(competitorList.getCompetitorById(competitorID).getFullDetails());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Competitor ID. Please enter a number.");
        }
    }

    private void recordScoresDialog() {
        String competitorIDString = JOptionPane.showInputDialog("Enter Competitor ID:");
        int competitorID = Integer.parseInt(competitorIDString);

        Competitor competitor = competitorList.getCompetitorById(competitorID);
        if (competitor != null) {
            String scoresString = JOptionPane.showInputDialog("Enter 5 scores separated by commas:");
            String[] scoresArray = scoresString.split(",");
            int[] newScores = Arrays.stream(scoresArray).mapToInt(Integer::parseInt).toArray();

            competitorList.recordScoresById(competitorID, newScores);
            displayCompetitors(); // Refresh the displayed competitors
        } else {
            JOptionPane.showMessageDialog(this, "Competitor not found.");
        }
    }
}
