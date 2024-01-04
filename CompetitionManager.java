import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompetitionManager {

    public static void main(String[] args) {
        // generateCompetitorList();

        // Assuming you already have the CompetitorList class to read competitors from
        // CSV
        CompetitorList competitorList = new CompetitorList("Competitors.csv");
        ArrayList<Competitor> competitors = competitorList.getCompetitors();

        // Create an instance of the Results class
        Result result = new Result(competitors);

        // Specify the file path for the report
        String reportFilePath = "CompetitionResultsReport.txt";

        // Generate the full report
        result.generateFullReport(reportFilePath);

        // Print a message indicating that the report has been generated
        System.out.println("Competition results report generated successfully. Check " + reportFilePath);
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
}