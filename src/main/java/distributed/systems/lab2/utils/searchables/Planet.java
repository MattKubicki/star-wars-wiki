package distributed.systems.lab2.utils.searchables;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import distributed.systems.lab2.communicaton.Sender;
import distributed.systems.lab2.utils.searchables.components.Specie;

import java.io.IOException;
import java.util.ArrayList;

public class Planet extends Searchable {
    private String name;
    private String climate;
    private String terrain;
    private String population;
    private ArrayList<String> residents;
    private ArrayList<String> films;
    private boolean mode;

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public ArrayList<String> getResidents() {
        return residents;
    }

    public void setResidents(ArrayList<String> residents) {
        this.residents = residents;
    }

    public ArrayList<String> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<String> films) {
        this.films = films;
    }

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

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    @Override
    public String toString() {
        if (mode) {
            return "Planet " + name + " which climate is " + climate +
                    " and most of its surface is covered with " + terrain +
                    " is inhabited by almost " + population + " creatures.\n" +
                    "Its most famous residents are " + getStringFromList(residents) + ". The planet is mentioned by "
                    + films.size() + " movies which are " + getStringFromList(films);
        }
        return "planet " + name + " which climate is " + climate +
                " and most of its surface is covered with " + terrain;
    }

    public Planet fillDataFromUrls(Sender sender) throws IOException {
        parsePeople(sender);
        parseMovies(sender);
        return this;
    }

    private void parsePeople(Sender sender) throws IOException {
        ArrayList<String> residents = getResidents();
        if (residents.size() == 0)
            return;
        ArrayList<String> parsedResidents = new ArrayList<>();
        for (int i=0; i<residents.size(); i++) {
            System.out.println("Parse resident " + i + " " + residents);
            String url = residents.get(i) + "?format=json";
            String raw = sender.getRaw(url);
            Person person = new Gson().fromJson(raw, new TypeToken<Person>(){}.getType());
            person.setMode(false);

            parseSpecies(person, sender);
            parsedResidents.add(person.toString());
        }
        setResidents(parsedResidents);
    }
}
