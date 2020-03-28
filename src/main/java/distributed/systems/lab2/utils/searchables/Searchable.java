package distributed.systems.lab2.utils.searchables;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import distributed.systems.lab2.communicaton.Sender;
import distributed.systems.lab2.utils.searchables.components.Specie;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Searchable {
    public abstract Searchable fillDataFromUrls(Sender sender) throws IOException;
    public abstract ArrayList<String> getFilms();
    public abstract void setFilms(ArrayList<String> films);
    protected String getStringFromList(ArrayList<String> list) {
        if (list.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (String element: list) {
            sb.append("#").append(element).append("* ");
        }
        return sb.toString();
    }

    protected void parseSpecies(Person person, Sender sender) throws IOException {
        String url = person.getSpecies().get(0) + "?format=json";
        String raw = sender.getRaw(url);
        Specie parsedSpecie = new Gson().fromJson(raw, new TypeToken<Specie>(){}.getType());
        parsedSpecie.setMode(false);
        ArrayList<String> species = person.getSpecies();
        species.add(0, parsedSpecie.toString());
        person.setSpecies(species);
    }

    protected void parseMovies(Sender sender) throws IOException {
        ArrayList<String> films = getFilms();
        if (films.size() == 0)
            return;
        ArrayList<String> parsedFilms = new ArrayList<>();
        for (int i=0; i<films.size(); i++) {
            System.out.println("Parse movie " + i + " " + films);
            String url = films.get(i) + "?format=json";
            String raw = sender.getRaw(url);
            Film parsedFilm = new Gson().fromJson(raw, new TypeToken<Film>(){}.getType());
            parsedFilm.setMode(false);
            parsedFilms.add(parsedFilm.toString());
        }
        setFilms(parsedFilms);
    }
}
