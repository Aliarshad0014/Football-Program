import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JTextArea;

public class CompetitorList {

    private ArrayList<Competitor> competitors;
    private String competitorsFile;

    public CompetitorList(String competitorsFile) {
        this.competitorsFile = competitorsFile;
        this.competitors = readCompetitorsFromFile();
    }

    public ArrayList<Competitor> readCompetitorsFromFile() {
        ArrayList<Competitor> competitors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(competitorsFile))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header line
                }

                String[] values = line.split(",");
                int competitorNumber = Integer.parseInt(values[0]);
                String firstName = values[1];
                String lastName = values[2];
                Name name = new Name(firstName, lastName);
                String email = values[3];
                String dateOfBirth = values[4];
                String category = values[5];
                int[] scores = new int[] {
                        Integer.parseInt(values[6]),
                        Integer.parseInt(values[7]),
                        Integer.parseInt(values[8]),
                        Integer.parseInt(values[9]),
                        Integer.parseInt(values[10])
                };

                Competitor competitor = new Competitor(competitorNumber, name, email, dateOfBirth, category, scores);
                competitors.add(competitor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return competitors;
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }

    // Get competitor by ID
    public Competitor getCompetitorById(int competitorID) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorID) {
                return competitor;
            }
        }
        return null;
    }

    // Add competitor to CSV file and update the data
    public void addCompetitorToCSV(Competitor newCompetitor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(competitorsFile, true))) {
            // Append new competitor details to the CSV file
            writer.write(String.format("%d,%s,%s,%s,%s,%s,%d,%d,%d,%d,%d\n",
                    newCompetitor.getCompetitorNumber(),
                    newCompetitor.getName().getFirstName(),
                    newCompetitor.getName().getLastName(),
                    newCompetitor.getEmail(),
                    newCompetitor.getDateOfBirth(),
                    newCompetitor.getCategory(),
                    newCompetitor.getScores()[0],
                    newCompetitor.getScores()[1],
                    newCompetitor.getScores()[2],
                    newCompetitor.getScores()[3],
                    newCompetitor.getScores()[4]));

            // Update the data in the ArrayList
            competitors.add(newCompetitor);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Remove competitor by ID from CSV file and update the data
    public void removeCompetitorByIdFromCSV(int competitorID) {
        // Get the competitor to be removed
        Competitor competitorToRemove = getCompetitorById(competitorID);

        if (competitorToRemove != null) {
            // Remove the competitor from the ArrayList
            competitors.remove(competitorToRemove);

            // Write the updated data to the CSV file
            writeCompetitorsToCSV();
        }
    }

    // Edit competitor's details in CSV file and update the data
    public void editCompetitorDetailsInCSV(int competitorID, Competitor newDetails) {
        // Get the competitor to be edited
        Competitor competitorToEdit = getCompetitorById(competitorID);

        if (competitorToEdit != null) {
            // Update the competitor's details in the ArrayList
            competitorToEdit.setName(newDetails.getName());
            competitorToEdit.setEmail(newDetails.getEmail());
            competitorToEdit.setDateOfBirth(newDetails.getDateOfBirth());
            competitorToEdit.setCategory(newDetails.getCategory());
            competitorToEdit.setScores(newDetails.getScores());

            // Write the updated data to the CSV file
            writeCompetitorsToCSV();
        }
    }

    public void recordCompetitorScoresById(int competitorID, int[] scores) {
        // Get the competitor to be recorded
        Competitor competitorToRecord = getCompetitorById(competitorID);

        if (competitorToRecord != null) {
            // Update the competitor's scores
            int[] currentScores = competitorToRecord.getScores();
            for (int i = 0; i < currentScores.length; i++) {
                currentScores[i] = scores[i];
            }

            // Update the data in the CSV file
            writeCompetitorsToCSV();
        }
    }

    // Helper method to write the competitors to CSV file
    private void writeCompetitorsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(competitorsFile))) {
            // Write header line
            writer.write(
                    "CompetitorNumber,FirstName,LastName,Email,DateOfBirth,Category,Score1,Score2,Score3,Score4,Score5\n");

            // Write each competitor's details to the CSV file
            for (Competitor competitor : competitors) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%d,%d,%d,%d,%d\n",
                        competitor.getCompetitorNumber(),
                        competitor.getName().getFirstName(),
                        competitor.getName().getLastName(),
                        competitor.getEmail(),
                        competitor.getDateOfBirth(),
                        competitor.getCategory(),
                        competitor.getScores()[0],
                        competitor.getScores()[1],
                        competitor.getScores()[2],
                        competitor.getScores()[3],
                        competitor.getScores()[4]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Enhanced method to get competitors' information in a tabular format
    public StringBuilder getCompetitorsInfoTable() {
        StringBuilder competitorsInfo = new StringBuilder();
        // Table header
        competitorsInfo.append(String.format("%-20s %-15s %-15s %-30s %-15s %-20s %-20s\n",
                "Competitor Number", "First Name", "Last Name", "Email", "Date of Birth", "Category", "Scores"));
        for (Competitor competitor : competitors) {
            competitorsInfo.append(String.format("%-20s %-15s %-15s %-30s %-15s %-20s %-20s\n",
                    competitor.getCompetitorNumber(),
                    competitor.getName().getFirstName(),
                    competitor.getName().getLastName(),
                    competitor.getEmail(),
                    competitor.getDateOfBirth(),
                    competitor.getCategory(),
                    Arrays.toString(competitor.getScores())));
        }
        return competitorsInfo;
    }

    // New method to print competitors to the console
    public void printCompetitorsToConsole() {
        System.out.println(getCompetitorsInfoTable().toString());
    }

    // New method to write competitors to a JTextArea
    public void writeCompetitorsToTextArea(JTextArea textArea) {
        textArea.setText(getCompetitorsInfoTable().toString());
    }

    // Method to sort competitors by competitor number
    public void sortCompetitorsByNumber() {
        competitors.sort(Comparator.comparingInt(Competitor::getCompetitorNumber));
    }

    // Method to sort competitors alphabetically by first name
    public void sortCompetitorsAlphabetically() {
        competitors.sort(Comparator.comparing(competitor -> competitor.getName().getFirstName()));
    }
}
