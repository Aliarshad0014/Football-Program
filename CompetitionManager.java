import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CompetitionManager {

    public static void main(String[] args) {
        // generateCompetitorList();
        // testStaffMethods();
        testOfficialsMethods();
        generateReport("CompetitionResultsReport.txt");
    }

    public static void generateReport(String fileName) {
        CompetitorList competitorList = new CompetitorList("Competitors.csv");
        ArrayList<Competitor> competitors = competitorList.getCompetitors();
        Result result = new Result(competitors);
        result.generateFullReport(fileName);
        System.out.println("Competition results report generated successfully. Check"
                + fileName);
    }

    public static void generateCompetitorList() {
        CompetitorList competitorList = new CompetitorList("Competitors.csv");
        ArrayList<Competitor> competitors = competitorList.getCompetitors();

        // Print competitor details
        for (Competitor competitor : competitors) {
            System.out.println("Competitor Number: " + competitor.getCompetitorNumber());
            System.out.println("Name: " + competitor.getName().getFullName());
            System.out.println("Email: " + competitor.getEmail());
            System.out.println("Date of Birth: " + competitor.getDateOfBirth());
            System.out.println("Category: " + competitor.getCategory());
            System.out.print("Scores: ");
            for (int score : competitor.getScores()) {
                System.out.print(score + " ");
            }
            System.out.println("\n");
        }
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