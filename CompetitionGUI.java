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

        JButton editDetailsButton = new JButton("Edit Details");
        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDetailsDialog();
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

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
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

    public void launchGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
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

}
