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
}