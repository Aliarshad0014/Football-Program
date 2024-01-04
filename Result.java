import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class Result {

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

    // Other summary statistics methods can be added as needed
}
