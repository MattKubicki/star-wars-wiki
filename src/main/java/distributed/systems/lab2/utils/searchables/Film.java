package distributed.systems.lab2.utils.searchables;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import distributed.systems.lab2.communicaton.Sender;
import distributed.systems.lab2.utils.searchables.components.Specie;

import java.io.IOException;
import java.util.ArrayList;

public class Film extends Searchable {
    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String release_date;
    private ArrayList<String> characters;
    private ArrayList<String> planets;
    private boolean mode;

    public ArrayList<String> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<String> planets) {
        this.planets = planets;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<String> characters) {
        this.characters = characters;
    }

    public String getOpeningCrawl() {
        return opening_crawl;
    }

    public void setOpeningCcrawl(String openingCrawl) {
        this.opening_crawl = openingCrawl;
    }

    public boolean getMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeId() {
        return episode_id;
    }

    public void setEpisodeId(int episodeId) {
        this.episode_id = episodeId;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    @Override
    public String toString() {
        if (mode) {
            return title + " is episode " + episode_id + " of the Saga.\n" +
                    "The movie was directed by " + director + " and released on " + release_date + ".\n" +
                    "It's famous opening crawl goes as follows: " + opening_crawl + ".\n" +
                    "We can see following characters in this movie: " + getStringFromList(characters) + ".\n" +
                    "They have many adventures in space and on planets such as: " + getStringFromList(planets);
        }
        return title + " which is episode " + episode_id + " of the Saga.\n" +
                "The movie was directed by " + director + " and released on " + release_date;
    }

    @Override
    public Searchable fillDataFromUrls(Sender sender) throws IOException {
        parsePeople(sender);
        parsePlanets(sender);
        return this;
    }

    private void parsePlanets(Sender sender) throws IOException {
        ArrayList<String> planets = getPlanets();
        if (planets.size() == 0)
            return;
        ArrayList<String> parsedPlanets = new ArrayList<>();
        for (int i=0; i<planets.size(); i++) {
            System.out.println("Parse planet " + i + " " + planets);
            String url = planets.get(i) + "?format=json";
            String raw = sender.getRaw(url);
            Planet planet = new Gson().fromJson(raw, new TypeToken<Planet>(){}.getType());
            planet.setMode(false);
            parsedPlanets.add(planet.toString());
        }
        setPlanets(parsedPlanets);
    }

    private void parsePeople(Sender sender) throws IOException {
        ArrayList<String> characters = getCharacters();
        if (characters.size() == 0)
            return;
        ArrayList<String> parsedCharacters = new ArrayList<>();
        for (int i=0; i<characters.size(); i++) {
            System.out.println("Parse character " + i + " " + characters);
            String url = characters.get(i) + "?format=json";
            String raw = sender.getRaw(url);
            Person person = new Gson().fromJson(raw, new TypeToken<Person>(){}.getType());
            person.setMode(false);

            parseSpecies(person, sender);
            parsedCharacters.add(person.toString());
        }
        setCharacters(parsedCharacters);
    }

    @Override
    public ArrayList<String> getFilms() {
        return null;
    }

    @Override
    public void setFilms(ArrayList<String> films) {
    }
}
