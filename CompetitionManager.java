import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompetitionManager {

    public static void main(String[] args) {
        CompetitionManager competitionManager = new CompetitionManager();
        competitionManager.generateCompetitorList();
    }

    public void generateCompetitorList() {
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