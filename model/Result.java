package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import java.util.Collections;

public class Result {
    private ArrayList<Competitor> competitors;
    private ArrayList<Staff> staffResponsible;
    private String summaryStatistics;
    private Competition competition;
    private Competitor winner;

    public Result(ArrayList<Competitor> competitors, ArrayList<Staff> staffResponsible,
            String summaryStatistics, Competition competition, Competitor winner) {
        this.competitors = competitors;
        this.staffResponsible = staffResponsible;
        this.summaryStatistics = summaryStatistics;
        this.competition = competition;
        this.winner = winner;
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(ArrayList<Competitor> competitors) {
        this.competitors = competitors;
    }

    public ArrayList<Staff> getStaffResponsible() {
        return staffResponsible;
    }

    public void setStaffResponsible(ArrayList<Staff> staffResponsible) {
        this.staffResponsible = staffResponsible;
    }

    public String getSummaryStatistics() {
        return summaryStatistics;
    }

    public void setSummaryStatistics(String summaryStatistics) {
        this.summaryStatistics = summaryStatistics;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Competitor getWinner() {
        return winner;
    }

    public void setWinner(Competitor winner) {
        this.winner = winner;
    }

    public static double calculateOverallScore(Competitor competitor) {
        int[] scores = competitor.getScores();
        int totalScore = 0;
        for (int score : scores) {
            totalScore += score;
        }
        return totalScore / (double) scores.length;
    }

    public static double calculateAverageTopNScores(Competitor competitor, int n) {
        int[] scores = competitor.getScores();
        if (n > scores.length) {
            throw new IllegalArgumentException("N cannot be greater than the number of scores.");
        }

        List<Integer> sortedScores = new ArrayList<>();
        for (int score : scores) {
            sortedScores.add(score);
        }
        sortedScores.sort(Collections.reverseOrder());

        double sumTopNScores = 0;
        for (int i = 0; i < n; i++) {
            sumTopNScores += sortedScores.get(i);
        }
        return sumTopNScores / n;
    }

    public static double calculateWeightedAverage(int[] scores) {
        if (scores.length == 0) {
            throw new IllegalArgumentException("Scores array cannot be empty.");
        }

        double weightedSum = 0;
        double weight = 1.0;

        for (int score : scores) {
            weightedSum += weight * score;
            weight += 0.5; // Adjust weights as needed
        }

        double totalWeight = (scores.length * (scores.length + 1)) / 4.0; // Sum of first n natural numbers / 2
        return weightedSum / totalWeight;
    }

    public static double calculateAverageIgnoringExtremes(Competitor competitor) {
        int[] scores = competitor.getScores();
        if (scores.length < 3) {
            throw new IllegalArgumentException("There must be at least three scores to ignore extremes.");
        }

        List<Integer> sortedScores = new ArrayList<>();
        for (int score : scores) {
            sortedScores.add(score);
        }
        sortedScores.sort(Collections.reverseOrder());

        int numberOfScoresToIgnore = 2;
        double sum = 0;
        for (int i = numberOfScoresToIgnore; i < sortedScores.size() - numberOfScoresToIgnore; i++) {
            sum += sortedScores.get(i);
        }
        return sum / (double) (sortedScores.size() - (2 * numberOfScoresToIgnore));
    }

    public static Competitor getCompetitorWithHighestOverallScore(ArrayList<Competitor> competitors) {
        Competitor highestScorer = null;
        double highestScore = Double.MIN_VALUE;

        for (Competitor competitor : competitors) {
            double overallScore = calculateOverallScore(competitor);
            if (overallScore > highestScore) {
                highestScore = overallScore;
                highestScorer = competitor;
            }
        }

        return highestScorer;
    }

    public static Map<Integer, Integer> calculateScoreFrequency(ArrayList<Competitor> competitors) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Competitor competitor : competitors) {
            for (int score : competitor.getScores()) {
                frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
            }
        }
        return frequencyMap;
    }

    // New method to generate a report as a string
    public String generateFullReportAsString() {
        StringBuilder report = new StringBuilder();

        // Table of competitors with full details
        report.append("Competitors Report\n");
        report.append(String.format("%-20s %-15s %-15s %-30s %-15s %-20s %-20s\n",
                "Competitor Number", "First Name", "Last Name", "Email", "Date of Birth", "Category", "Scores"));
        for (Competitor competitor : competitors) {
            report.append(String.format("%-20s %-15s %-15s %-30s %-15s %-20s %-20s\n",
                    competitor.getCompetitorNumber(),
                    competitor.getName().getFirstName(),
                    competitor.getName().getLastName(),
                    competitor.getEmail(),
                    competitor.getDateOfBirth(),
                    competitor.getCategory(),
                    scoresToString(competitor.getScores())));
        }

        // Details of the competitor with the highest overall score
        Competitor highestScorer = getCompetitorWithHighestOverallScore(competitors);
        report.append("\nCompetitor with the Highest Overall Score:\n");
        report.append(String.format("Competitor Number: %s\n", highestScorer.getCompetitorNumber()));
        report.append(String.format("Competitor Name: %s\n", highestScorer.getName().getFullName()));
        report.append(String.format("Overall Score: %.2f\n", calculateOverallScore(highestScorer)));

        // Four other summary statistics
        report.append("\nSummary Statistics:\n");
        report.append(String.format("Average Top 3 Scores: %.2f\n", calculateAverageTopNScores(highestScorer, 3)));
        report.append(String.format("Weighted Average: %.2f\n", calculateWeightedAverage(highestScorer.getScores())));
        report.append(
                String.format("Average Ignoring Extremes: %.2f\n", calculateAverageIgnoringExtremes(highestScorer)));

        // Frequency report
        report.append("\nScore Frequency Report:\n");
        Map<Integer, Integer> scoreFrequency = calculateScoreFrequency(competitors);
        for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet()) {
            report.append(String.format("Score: %d, Frequency: %d\n", entry.getKey(), entry.getValue()));
        }

        return report.toString();
    }

    public String generateShortReportAsString() {
        StringBuilder report = new StringBuilder();

        // Details of the competitor with the highest overall score
        Competitor highestScorer = getCompetitorWithHighestOverallScore(competitors);
        report.append("Competition Winner:\n");
        report.append("Competitor with the Highest Overall Score:\n");
        report.append(String.format("Competitor Number: %s\n", highestScorer.getCompetitorNumber()));
        report.append(String.format("Competitor Name: %s\n", highestScorer.getName().getFullName()));
        report.append(String.format("Overall Score: %.2f\n", calculateOverallScore(highestScorer)));

        return report.toString();
    }

    public void writeReportToFile(String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            String report = generateFullReportAsString();
            writer.write(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String scoresToString(int[] scores) {
        StringBuilder sb = new StringBuilder();
        for (int score : scores) {
            sb.append(score).append(" ");
        }
        return sb.toString().trim();
    }

    public String generateSummaryReportAsString() {
        StringBuilder summaryReport = new StringBuilder();
        summaryReport.append("Summary Report:\n");

        // Count of competitors
        int totalCompetitors = competitors.size();
        summaryReport.append("There are ").append(totalCompetitors).append(" competitors\n");

        // Competitor with the highest score
        Competitor highestScorer = getCompetitorWithHighestOverallScore(competitors);
        summaryReport.append("The person with the highest score is ")
                .append(highestScorer.getName().getFullName())
                .append(" with a score of ")
                .append(calculateOverallScore(highestScorer))
                .append(".\n");

        // Frequency report
        summaryReport.append("The following individual scores were awarded:\n");
        Map<Integer, Integer> scoreFrequency = calculateScoreFrequency(competitors);
        summaryReport.append(String.format("%-10s %-10s\n", "Score :", "Frequency:"));
        for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet()) {
            summaryReport.append(String.format("%-10d %-10d\n", entry.getKey(), entry.getValue()));
        }

        return summaryReport.toString();
    }

    public void printSummaryToConsole() {
        System.out.println(generateSummaryReportAsString());
    }
}
