package distributed.systems.lab2.utils.searchables;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import distributed.systems.lab2.communicaton.Sender;
import distributed.systems.lab2.utils.searchables.components.Specie;
import distributed.systems.lab2.utils.searchables.components.Starship;
import distributed.systems.lab2.utils.searchables.components.Vehicle;

import java.io.IOException;
import java.util.ArrayList;

public class Person extends Searchable {
    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private String homeworld;
    private ArrayList<String> films;
    private ArrayList<String> species;
    private ArrayList<String> vehicles;
    private ArrayList<String> starships;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hair_color;
    }

    public void setHairColor(String hairColor) {
        this.hair_color = hairColor;
    }

    public String getSkinColor() {
        return skin_color;
    }

    public void setSkinColor(String skinColor) {
        this.skin_color = skinColor;
    }

    public String getEyeColor() {
        return eye_color;
    }

    public void setEyecolor(String eyeColor) {
        this.eye_color = eyeColor;
    }

    public String getBirthYear() {
        return birth_year;
    }

    public void setBirth_year(String birthYear) {
        this.birth_year = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public ArrayList<String> getFilms(){
        return films;
    }

    public void setFilms(ArrayList<String> films) {
        this.films = films;
    }

    public ArrayList<String> getSpecies() {
        return species;
    }

    public void setSpecies(ArrayList<String> species) {
        this.species = species;
    }

    public ArrayList<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<String> vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<String> getStarships() {
        return starships;
    }

    public void setStarships(ArrayList<String> starships) {
        this.starships = starships;
    }

    @Override
    public String toString() {
        String pronoun = "She";
        if (gender.equals("male"))
            pronoun = "He";

        //person could be seen in movies as young brown haired man or old bald man
        //I chose young version
        if (getHairColor().contains(","))
            setHairColor(getHairColor().substring(0, getHairColor().indexOf(',')));

        if (mode) {
            String result = name + " was a " + gender + "  " + species.get(0) + ".\n" +
                    name + " comes from " + homeworld + ". " + pronoun + " was born there in " + birth_year
                    + ".\n" + pronoun + " is " + height + " cm high and weighs " + mass + " kgs.\n"
                    + pronoun + " has " + hair_color + " hairs, " + skin_color + " skin and " + eye_color + " eyes.\n"
                    + "We can meet " + name + " on the screen in following movies: " + getStringFromList(films) + ".\n";

            String end = "";
            if (vehicles.size() == 0 && starships.size() == 0) {
                end = "In the movies we cannot see " + name + " in vehicles or starships";
            } else {
                end = "In the movies we can see " + name + " in following vehicles and starships: "
                        + getStringFromList(vehicles) + " " + getStringFromList(starships);
            }

            return result + end;
        }
        return name + " who was a " + gender + "  " + species.get(0) + ".\n"
                + " and was born there in " + birth_year + ".\n"
                + pronoun + " has " + hair_color + " hairs, " + skin_color + " skin and " + eye_color + " eyes.\n";
    }

    public Person fillDataFromUrls(Sender sender) throws IOException {
        String url = getSpecies().get(0) + "?format=json";
        String raw = sender.getRaw(url);
        Specie parsedSpecie = new Gson().fromJson(raw, new TypeToken<Specie>(){}.getType());
        parsedSpecie.setMode(true);

        url = parsedSpecie.getHomeworld() + "?format=json";
        raw = sender.getRaw(url);
        Planet parsedPlanet = new Gson().fromJson(raw, new TypeToken<Planet>(){}.getType());
        parsedSpecie.setHomeworld(parsedPlanet.toString());
        parsedPlanet.setMode(false);

        ArrayList<String> species = getSpecies();
        species.add(0, parsedSpecie.toString());
        setSpecies(species);

        url = getHomeworld() + "?format=json";
        raw = sender.getRaw(url);
        parsedPlanet = new Gson().fromJson(raw, new TypeToken<Planet>(){}.getType());
        setHomeworld(parsedPlanet.toString());
        parsedPlanet.setMode(false);

        parseMovies(sender);

        parseVehicles(sender);

        parseStarships(sender);

        return this;
    }

    private void parseStarships(Sender sender) throws IOException {
        ArrayList<String> starships = getStarships();
        if (starships.size() == 0)
            return;
        ArrayList<String> parsedStarships = new ArrayList<>();
        for (int i=0; i<starships.size(); i++) {
            System.out.println("Parse starship " + i + " " + starships);
            String url = starships.get(i) + "?format=json";
            String raw = sender.getRaw(url);
            Starship starship = new Gson().fromJson(raw, new TypeToken<Starship>(){}.getType());
            starship.setMode(false);
            parsedStarships.add(starship.toString());
        }
        setStarships(parsedStarships);
    }

    private void parseVehicles(Sender sender) throws IOException {
        ArrayList<String> vehicles = getVehicles();
        if (vehicles.size() == 0)
            return;
        ArrayList<String> parsedVehicles = new ArrayList<>();
        for (int i=0; i<vehicles.size(); i++) {
            System.out.println("Parse vehicle " + i + " " + vehicles);
            String url = vehicles.get(i) + "?format=json";
            String raw = sender.getRaw(url);
            Vehicle vehicle = new Gson().fromJson(raw, new TypeToken<Vehicle>(){}.getType());
            vehicle.setMode(false);
            parsedVehicles.add(vehicle.toString());
        }
        setVehicles(parsedVehicles);
    }
}
