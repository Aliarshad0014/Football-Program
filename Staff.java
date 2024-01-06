public class Staff {

    private int ID;
    private Name name;
    private String accessLevel;
    private boolean competitorDetailsRecorded;

    // Constructors
    public Staff(int ID, Name name, String accessLevel) {
        this.ID = ID;
        this.name = name;
        this.accessLevel = accessLevel;
        this.competitorDetailsRecorded = false;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean isCompetitorDetailsRecorded() {
        return competitorDetailsRecorded;
    }

    public void setCompetitorDetailsRecorded(boolean competitorDetailsRecorded) {
        this.competitorDetailsRecorded = competitorDetailsRecorded;
    }

    // Record competitor scores by ID
    public void recordCompetitorScoresById(CompetitorList competitorList, int competitorID, int[] scores) {
        competitorList.recordCompetitorScoresById(competitorID, scores);
    }
}
