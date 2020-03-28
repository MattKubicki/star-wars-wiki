package distributed.systems.lab2.communicaton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import distributed.systems.lab2.utils.searchables.Film;
import distributed.systems.lab2.utils.searchables.Person;
import distributed.systems.lab2.utils.searchables.Planet;
import distributed.systems.lab2.utils.searchables.Searchable;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

public class Sender {

    private ArrayList<Searchable> getJson(String person, String planet, String movie) throws IOException {
        Sender.disableCertificateValidation();
        ArrayList<Searchable> result = new ArrayList<>();
        if (!person.equals("")) {
            String url = "https://swapi.co/api/people/?search=";
            url += person;
            url += "&format=json";
            String raw = getRaw(url);

            System.out.println(raw);

            Type type = new TypeToken<Person>(){}.getType();
            System.out.println(dropMeta(raw));
            Person parsedPerson = new Gson().fromJson(dropMeta(raw), type);
            if (parsedPerson != null) {
                parsedPerson.setMode(true);

                System.out.println(parsedPerson);

                result.add(parsedPerson);
            }
        }
        if (!planet.equals("")) {
            String url = "https://swapi.co/api/planets/?search=";
            url += planet;
            url += "&format=json";
            String raw = getRaw(url);

            System.out.println(raw);

            Type type = new TypeToken<Planet>(){}.getType();
            Planet parsedPlanet = new Gson().fromJson(dropMeta(raw), type);
            if (parsedPlanet != null) {
                parsedPlanet.setMode(true);

                System.out.println(parsedPlanet);

                result.add(parsedPlanet);
            }
        }
        if (!movie.equals("")) {
            String url = "https://swapi.co/api/films/?search=";
            url += movie;
            url += "&format=json";
            String raw = getRaw(url);

            System.out.println(raw);

            Type type = new TypeToken<Film>(){}.getType();
            Film parsedMovie = new Gson().fromJson(dropMeta(raw), type);
            if (parsedMovie != null) {
                parsedMovie.setMode(true);

                System.out.println(parsedMovie);

                result.add(parsedMovie);
            }
        }
        return result;
    }

    private String dropMeta(String raw) {
        int index = raw.indexOf(":") + 1;
        int countValue = Integer.parseInt(String.valueOf(raw.charAt(index)));
        if (countValue != 1){
            return "";
        }
        return raw.substring(raw.indexOf("results") + 10, raw.lastIndexOf('}') - 1);
    }

    public String getRaw(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();

        String inputLine = in.readLine();
        while(inputLine != null){
            response.append(inputLine);
            inputLine = in.readLine();
        }
        in.close();
        connection.disconnect();

        return response.toString();
    }

    public ArrayList<String> respond(String person, String planet, String spaceship) throws IOException {
        ArrayList<Searchable> responses = getJson(person, planet, spaceship);
        ArrayList<String> result = new ArrayList<>();
        if (responses.size() == 0) {
            result.add("Too many articles or no article found. Try to place more precise query");
            return result;
        }

        for (Searchable response: responses) {
            result.add(response.fillDataFromUrls(this).toString());
        }
        return result;
    }

    //method needed to get rid of SSL handshake error
    public static void disableCertificateValidation() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }};

        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");

            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {}
    }
}
