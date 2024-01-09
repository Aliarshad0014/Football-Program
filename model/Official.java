package model;

import controller.CompetitorList;

public class Official {

    private Name name;
    private int ID;

    // Constructors
    public Official(int ID, Name name) {
        this.ID = ID;
        this.name = name;
    }

    // Getters and Setters
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // Amend competitor details
    public void amendCompetitorDetails(CompetitorList competitorList, int id, Competitor newDetails) {
        competitorList.editCompetitorDetailsById(id, newDetails);
    }

    // Register competitor on arrival
    public void registerCompetitorOnArrival(CompetitorList competitorList, Competitor competitor) {
        competitorList.addCompetitorToCSV(competitor);
    }

    // Remove non-compliant competitor
    public void removeNonCompliantCompetitor(CompetitorList competitorList, int competitorID) {
        competitorList.removeCompetitorByIdFromCSV(competitorID);
    }
}
