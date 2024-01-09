package model;

import java.util.ArrayList;

public class Competition {
    private int id;
    private String competitionName;
    private String[] categories;
    private ArrayList<Competitor> competitors;

    public Competition(int id, String competitionName, String[] categories, ArrayList<Competitor> competitors) {
        this.id = id;
        this.competitionName = competitionName;
        this.categories = categories;
        this.competitors = competitors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(ArrayList<Competitor> competitors) {
        this.competitors = competitors;
    }
}
