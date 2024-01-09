package controller;

import java.util.ArrayList;

import model.Competitor;
import model.Name;
import model.Official;
import model.Result;
import model.Staff;
import view.CompetitionGUI;

public class CompetitionManager {

    public static void main(String[] args) {
        // generateCompetitorList();
        // testStaffMethods();
        // testOfficialsMethods();
        // generateReport("CompetitionResultsReport.txt");
        launchGUI();
    }

    public static void launchGUI() {
        CompetitorList competitorList = new CompetitorList("Competitors.csv");

        CompetitionGUI competitionGUI = new CompetitionGUI(competitorList);
        competitionGUI.launchGUI();
    }

    public static void generateReport(String fileName) {
        CompetitorList competitorList = new CompetitorList("Competitors.csv");
        ArrayList<Competitor> competitors = competitorList.getCompetitors();
        Result result = new Result(competitors, null, null, null, null);
        result.writeReportToFile(fileName);
        System.out.println("Competition results report generated successfully. Check"
                + fileName);
    }

    public static void generateCompetitorList() {
        CompetitorList competitorList = new CompetitorList("Competitors.csv");
        competitorList.printCompetitorsToConsole();
    }

    public static void testOfficialsMethods() {
        CompetitorList competitorList = new CompetitorList("Competitors.csv");

        // Sample competitor data
        Competitor newCompetitor = new Competitor(14, new Name("John", "Doe"),
                "john.doe@example.com",
                "1990-01-01", "Men's Singles", new int[] { 1, 2, 3, 4, 5 });

        // Sample competitor ID
        int competitorID = 6;
        int competitorIDToRemove = 8;

        // Sample new competitor details
        Competitor newDetails = new Competitor(6, new Name("Updated", "Competitor"),
                "updated@example.com",
                "1990-01-01", "Updated Category", new int[] { 5, 4, 3, 2, 1 });

        // Test the methods
        Official official = new Official(1, null);
        official.amendCompetitorDetails(competitorList, competitorID, newDetails);
        official.registerCompetitorOnArrival(competitorList, newCompetitor);
        official.removeNonCompliantCompetitor(competitorList, competitorIDToRemove);
    }

    public static void testStaffMethods() {
        int competitorIDToRecordScores = 9;
        int[] scoresToRecord = { 0, 0, 0, 0, 0 };

        CompetitorList competitorList = new CompetitorList("Competitors.csv");

        Staff staff = new Staff(1, null, null);
        staff.recordCompetitorScoresById(competitorList, competitorIDToRecordScores,
                scoresToRecord);
    }
}