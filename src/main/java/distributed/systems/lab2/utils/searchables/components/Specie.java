package distributed.systems.lab2.utils.searchables.components;

import java.util.ArrayList;

public class Specie {
    private String name;
    private String classification;
    private String average_lifespan;
    private String homeworld;
    private String language;
    private ArrayList<String> people;
    private boolean mode;

    public boolean getMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAverageLifespan() {
        return average_lifespan;
    }

    public void setAverageLifespan(String averageLifespan) {
        this.average_lifespan = averageLifespan;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<String> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<String> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        String lifespanDescription = "";
        if (average_lifespan.equals("unknown"))
            lifespanDescription = average_lifespan + ".\n";
        else
            lifespanDescription = "about " + average_lifespan + " years.\n";

        if (mode) {
            return name + ", which is classified as a " + classification + " and its average lifespan is "
                    + lifespanDescription + name + "s originate from " + homeworld + ".\n" +
                    "Their most common language is " + language;
        }
        return name + ", which is classified as a " + classification + " and its average lifespan is "
                + lifespanDescription + "Their most common language is " + language;
    }
}
