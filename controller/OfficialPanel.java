package controller;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import model.Competitor;
import controller.CompetitorList;
import model.Name;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class OfficialPanel extends JFrame {
    private CompetitorList competitorList;
    private JTextArea competitorsTextArea;

    public OfficialPanel(CompetitorList competitorList) {
        this.competitorList = competitorList;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Official Panel");

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
        JButton amendDetailsButton = createButton("Amend Competitor Details", buttonFont, e -> editDetailsDialog());
        JButton registerCompetitorButton = createButton("Register Competitor", buttonFont, e -> registerCompetitor());
        JButton removeCompetitorButton = createButton("Remove Non-compliant Competitor", buttonFont,
                e -> removeNonCompliantCompetitor());

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
        panel.add(amendDetailsButton);
        panel.add(registerCompetitorButton);
        panel.add(removeCompetitorButton);
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

    private Competitor getCompetitorDetailsFromDialog() {
        // Input fields
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String dateOfBirth = JOptionPane.showInputDialog("Enter Date of Birth:");
        String category = JOptionPane.showInputDialog("Enter Category:");

        // Scores input
        int[] scores = new int[5];
        for (int i = 0; i < 5; i++) {
            String scoreInput = JOptionPane.showInputDialog("Enter Score " + (i + 1) + ":");
            scores[i] = Integer.parseInt(scoreInput);
        }

        // Create a new Competitor object with the entered details
        Name name = new Name(firstName, lastName);
        return new Competitor(0, name, email, dateOfBirth, category, scores);
    }

    private void editDetailsDialog() {
        String competitorIDString = JOptionPane.showInputDialog("Enter Competitor ID:");
        int competitorID = Integer.parseInt(competitorIDString);

        Competitor competitor = competitorList.getCompetitorById(competitorID);
        if (competitor != null) {
            // Create a new Competitor with updated details
            Competitor newDetails = getCompetitorDetailsFromDialog();

            // Edit competitor details using the method in CompetitorList
            competitorList.editCompetitorDetailsById(competitorID, newDetails);
            displayCompetitors(); // Refresh the displayed competitors
        } else {
            JOptionPane.showMessageDialog(this, "Competitor not found.");
        }
    }

    private void registerCompetitor() {
        // Create a dialog box to input competitor details
        JTextField competitorNumberField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField score1Field = new JTextField();
        JTextField score2Field = new JTextField();
        JTextField score3Field = new JTextField();
        JTextField score4Field = new JTextField();
        JTextField score5Field = new JTextField();

        Object[] message = {
                "Competitor Number:", competitorNumberField,
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Email:", emailField,
                "Date of Birth:", dobField,
                "Category:", categoryField,
                "Score 1:", score1Field,
                "Score 2:", score2Field,
                "Score 3:", score3Field,
                "Score 4:", score4Field,
                "Score 5:", score5Field
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register Competitor", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Parse input values
                int competitorNumber = Integer.parseInt(competitorNumberField.getText());
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String dob = dobField.getText();
                String category = categoryField.getText();
                int[] scores = {
                        Integer.parseInt(score1Field.getText()),
                        Integer.parseInt(score2Field.getText()),
                        Integer.parseInt(score3Field.getText()),
                        Integer.parseInt(score4Field.getText()),
                        Integer.parseInt(score5Field.getText())
                };

                // Create a new Competitor object
                Competitor newCompetitor = new Competitor(competitorNumber,
                        new Name(firstName, lastName),
                        email, dob, category, scores);

                // Register the competitor
                competitorList.addCompetitorToCSV(newCompetitor);

                // Refresh the displayed competitors
                displayCompetitors();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Invalid input. Please enter numeric values for Competitor Number and Scores.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeNonCompliantCompetitor() {
        // Prompt user for competitor ID
        String idInput = JOptionPane.showInputDialog("Enter Competitor ID to Remove:");
        try {
            int competitorID = Integer.parseInt(idInput);

            // Remove non-compliant competitor
            competitorList.removeCompetitorByIdFromCSV(competitorID);
            JOptionPane.showMessageDialog(null, "Competitor removed successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Competitor ID. Please enter a valid number.");
        }
    }
}
