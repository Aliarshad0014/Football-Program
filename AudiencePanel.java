import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AudiencePanel extends JFrame {
    private CompetitorList competitorList;
    private JTextArea competitorsTextArea;

    public AudiencePanel(CompetitorList competitorList) {
        this.competitorList = competitorList;
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Audience Panel");

        Font buttonFont = new Font("Arial", Font.PLAIN, 12);

        JButton viewCompetitorsButton = createButton("View Competitors", buttonFont, e -> displayCompetitors());
        JButton sortCompetitorsByNumberButton = createButton("Sort by Number", buttonFont,
                e -> sortByCompetitorNumber());
        JButton sortCompetitorsAlphabeticallyButton = createButton("Sort Alphabetically", buttonFont,
                e -> sortByAlphabeticalOrder());
        JButton generateReportButton = createButton("View Competition Report", buttonFont,
                e -> generateCompetitionReport());

        competitorsTextArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(competitorsTextArea);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(viewCompetitorsButton);
        panel.add(sortCompetitorsByNumberButton);
        panel.add(sortCompetitorsAlphabeticallyButton);
        panel.add(generateReportButton);
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
        // Create an instance of the Result class
        Result result = new Result(competitorList.getCompetitors());

        // Use the writeReportToTextArea method to display the report in the JTextArea
        result.writeReportToTextArea(competitorsTextArea);
    }
}
