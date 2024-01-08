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

        JButton openStaffPanelButton = new JButton("Open Staff Panel");
        JButton openOfficialsPanelButton = new JButton("Open Officials Panel");
        JButton openCompetitorsPanelButton = new JButton("Open Competitors Panel");
        JButton openAudiencePanelButton = new JButton("Open Audience Panel");

        openStaffPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStaffPanel();
            }
        });

        openOfficialsPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOfficialsPanel();
            }
        });

        openCompetitorsPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCompetitorsPanel();
            }
        });

        openAudiencePanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAudiencePanel();
            }
        });

        JPanel panel = new JPanel();
        panel.add(openStaffPanelButton);
        panel.add(openOfficialsPanelButton);
        panel.add(openCompetitorsPanelButton);
        panel.add(openAudiencePanelButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void openStaffPanel() {
        StaffPanel staffPanel = new StaffPanel(competitorList);
        staffPanel.launchGUI();
        dispose();
    }

    private void openOfficialsPanel() {
        OfficialPanel officialsPanel = new OfficialPanel(competitorList);
        officialsPanel.launchGUI();
        dispose();
    }

    private void openCompetitorsPanel() {
        CompetitorPanel competitorsPanel = new CompetitorPanel(competitorList);
        competitorsPanel.launchGUI();
        dispose();
    }

    private void openAudiencePanel() {
        AudiencePanel audiencePanel = new AudiencePanel(competitorList);
        audiencePanel.launchGUI();
        dispose();
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
