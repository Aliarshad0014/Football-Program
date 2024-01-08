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
        }); //

        JButton sortCompetitorsByNumberButton = new JButton("View Competitors Sorted By Number");
        sortCompetitorsByNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortByCompetitorNumber();
            }
        });

        JButton sortCompetitorsAlbhabeticallyButton = new JButton("View Competitors Sorted Alphabetically");
        sortCompetitorsAlbhabeticallyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortByAlphabeticalOrder();
            }
        });

        JButton generateReportButton = new JButton("Generate Competition Report");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCompetitionReport();
            }
        });

        JButton viewScoresByIdButton = new JButton("View Scores by ID");
        viewScoresByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayScoresById();
            }
        });

        JButton recordScoresButton = new JButton("Record Scores");
        recordScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordScoresDialog();
            }
        });

        JButton editDetailsButton = new JButton("Amend Competitor Details");
        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDetailsDialog();
            }
        });

        JButton registerCompetitorButton = new JButton("Register Competitor");
        registerCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCompetitor();
            }
        });

        JButton removeNonCompliantButton = new JButton("Remove Non-Compliant Competitor");
        removeNonCompliantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeNonCompliantCompetitor();
            }
        });

        JButton generateSummaryButton = new JButton("Generate Summary Report");
        generateSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySummaryReport();
            }
        });

        competitorsTextArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(competitorsTextArea);

        JPanel panel = new JPanel();
        panel.add(viewCompetitorsButton);
        panel.add(sortCompetitorsByNumberButton);
        panel.add(sortCompetitorsAlbhabeticallyButton);
        panel.add(generateReportButton);
        panel.add(viewScoresByIdButton);
        panel.add(scrollPane);
        panel.add(recordScoresButton);
        panel.add(editDetailsButton);
        panel.add(registerCompetitorButton);
        panel.add(removeNonCompliantButton);
        panel.add(generateSummaryButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
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
        // Create an instance of the Result class
        Result result = new Result(competitorList.getCompetitors());

        // Use the writeReportToTextArea method to display the report in the JTextArea
        result.writeReportToTextArea(competitorsTextArea);
    }

    private void displaySummaryReport() {
        Result result = new Result(competitorList.getCompetitors());
        result.displaySummaryInGUI(competitorsTextArea);
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
