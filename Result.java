import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class Result {

    private ArrayList<Competitor> competitors;

    public Result(ArrayList<Competitor> competitors) {
        this.competitors = competitors;
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

    public void generateFullReport(String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            // Table of competitors with full details
            writer.write("Competitors Report\n");
            writer.write(String.format("%-20s %-15s %-15s %-30s %-15s %-20s %-20s\n",
                    "Competitor Number", "First Name", "Last Name", "Email", "Date of Birth", "Category", "Scores"));
            for (Competitor competitor : competitors) {
                writer.write(String.format("%-20s %-15s %-15s %-30s %-15s %-20s %-20s\n",
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
            writer.write("\nCompetitor with the Highest Overall Score:\n");
            writer.write(String.format("Competitor Number: %s\n", highestScorer.getCompetitorNumber()));
            writer.write(String.format("Competitor Name: %s\n", highestScorer.getName().getFullName()));
            writer.write(String.format("Overall Score: %.2f\n", calculateOverallScore(highestScorer)));

            // Four other summary statistics
            writer.write("\nSummary Statistics:\n");
            writer.write(String.format("Average Top 3 Scores: %.2f\n", calculateAverageTopNScores(highestScorer, 3)));
            writer.write(
                    String.format("Weighted Average: %.2f\n", calculateWeightedAverage(highestScorer.getScores())));
            writer.write(String.format("Average Ignoring Extremes: %.2f\n",
                    calculateAverageIgnoringExtremes(highestScorer)));

            // Frequency report
            writer.write("\nScore Frequency Report:\n");
            Map<Integer, Integer> scoreFrequency = calculateScoreFrequency(competitors);
            for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet()) {
                writer.write(String.format("Score: %d, Frequency: %d\n", entry.getKey(), entry.getValue()));
            }

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

}
