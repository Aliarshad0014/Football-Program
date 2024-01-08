import java.util.Arrays;

public class Competitor {
    private int competitorNumber;
    private Name name;
    private String email;
    private String dateOfBirth;
    private String category;
    private int[] scores;

    public Competitor(int competitorNumber, Name name, String email, String dateOfBirth, String category,
            int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.category = category;
        this.scores = scores;
    }

    // Getters and setters for Competitor fields
    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCategory() {
        return category;
    }

    public int[] getScores() {
        return scores;
    }

    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Competitor number %d, name %s.%n",
                competitorNumber, name.getFullName()));

        details.append(String.format("%s is a %s aged %d and received these scores: %s%n",
                name.getFirstName(), category, calculateAge(), Arrays.toString(scores)));

        details.append(String.format("This gives him/her an overall score of %.2f.%n", calculateOverallScore()));

        return details.toString();
    }

    public void printToConsole() {
        System.out.println(getFullDetails());
    }

    public void appendToTextArea(StringBuilder builder) {
        builder.append(getFullDetails()).append("\n");
    }

    private int calculateAge() {
        int currentYear = java.time.Year.now().getValue();
        int birthYear = Integer.parseInt(dateOfBirth.substring(0, 4));
        return currentYear - birthYear;
    }

    private double calculateOverallScore() {
        int sum = Arrays.stream(scores).sum();
        return (double) sum / scores.length;
    }

}