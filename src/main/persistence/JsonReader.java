package persistence;

import model.Event;
import model.EventLog;
import model.Pokemon;
import model.PokemonTeam;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// CODE REFERENCES:
// JsonSerializationDemo - JsonReader
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads the pokemon team and score from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads pokemon team from file and returns it;
    // throws IOException if an error occurs when reading data from the file
    public PokemonTeam read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded pokemon team from: " + source));
        return parsePokemonTeam(jsonObject);
    }

    // EFFECTS: reads the source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses pokemon team from the JSON object and returns it
    private PokemonTeam parsePokemonTeam(JSONObject jsonObject) {
        String teamName = jsonObject.getString("team name");
        PokemonTeam pt = new PokemonTeam(teamName);
        addPokemons(pt, jsonObject);
        addScores(pt, jsonObject);
        return pt;
    }

    // MODIFIES: pt
    // EFFECTS: parses pokemons from the JSON object and adds them to the pokemon team
    private void addPokemons(PokemonTeam pt, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pokemons");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemon(pt, nextPokemon);
        }
    }

    // MODIFIES: pt
    // EFFECTS: parses pokemon from the JSON object and adds it to the pokemon team
    private void addPokemon(PokemonTeam pt, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int health = jsonObject.getInt("health");
        Pokemon pokemon = new Pokemon(name, health);
        pt.addPokemon(pokemon);
    }

    // MODIFIES: pt
    // EFFECTS: parses scores from JSON object and adds them to the pokemon team
    private void addScores(PokemonTeam pt, JSONObject jsonObject) {
        int numberOfLosses = jsonObject.getInt("number of losses");
        int numberOfTies = jsonObject.getInt("number of ties");
        int numberOfWins = jsonObject.getInt("number of wins");
        pt.setNumberOfLosses(numberOfLosses);
        pt.setNumberOfTies(numberOfTies);
        pt.setNumberOfWins(numberOfWins);
    }
}
