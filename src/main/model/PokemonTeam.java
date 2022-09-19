package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// CODE REFERENCES:
// JsonSerializationDemo - WorkRoom
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a pokemon team with a name, a list of pokemons, number of wins, number of ties, and number of losses
public class PokemonTeam implements Writable {
    private String teamName;
    private ArrayList<Pokemon> listOfPokemons;
    private int numberOfWins;
    private int numberOfTies;
    private int numberOfLosses;

    // REQUIRES: given name must be non-zero in length
    // EFFECTS: constructs a pokemon team with the given name, an empty list of pokemons, and the number of
    //          wins, ties, losses starting at 0.
    public PokemonTeam(String name) {
        this.teamName = name;
        listOfPokemons = new ArrayList<>();
        numberOfWins = 0;
        numberOfTies = 0;
        numberOfLosses = 0;
    }

    // REQUIRES: given p must not be null
    // MODIFIES: this
    // EFFECTS: adds the given pokemon to the listOfPokemons
    public void addPokemon(Pokemon p) {
        EventLog.getInstance().logEvent(new Event("Added a pokemon: " + p.getName()));
        listOfPokemons.add(p);
    }

    // REQUIRES: given p must not be null and is already in the list
    // MODIFIES: this
    // EFFECTS: removes the given pokemon from the listOfPokemons
    public void removePokemon(Pokemon p) {
        EventLog.getInstance().logEvent(new Event("Removed a pokemon: " + p.getName()));
        listOfPokemons.remove(p);
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to numberOfWins
    public void addOneToWins() {
        EventLog.getInstance().logEvent(new Event("Won a pokemon battle."));
        numberOfWins++;
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to numberOfTies
    public void addOneToTies() {
        EventLog.getInstance().logEvent(new Event("Tied a pokemon battle."));
        numberOfTies++;
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to the numberOfLosses
    public void addOneToLosses() {
        EventLog.getInstance().logEvent(new Event("Lost a pokemon battle."));
        numberOfLosses++;
    }

    // EFFECTS: returns a list of names of all the pokemons on the team
    public ArrayList<String> getAllPokemonNames() {
        ArrayList<String> listOfPokemonNames = new ArrayList<>();
        for (Pokemon next : listOfPokemons) {
            listOfPokemonNames.add(next.getName());
        }
        return listOfPokemonNames;
    }

    // EFFECTS: returns this (PokemonTeam) as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("team name", teamName);
        json.put("pokemons", listOfPokemonsToJson());
        json.put("number of wins", numberOfWins);
        json.put("number of losses", numberOfLosses);
        json.put("number of ties", numberOfTies);
        return json;
    }

    // EFFECTS: returns listOfPokemons in this pokemon team as a JSON array
    public JSONArray listOfPokemonsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Pokemon p : listOfPokemons) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    public String getTeamName() {
        return teamName;
    }

    public ArrayList<Pokemon> getListOfPokemons() {
        return listOfPokemons;
    }

    public int getNumberOfLosses() {
        return numberOfLosses;
    }

    public int getNumberOfTies() {
        return numberOfTies;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfLosses(int number) {
        numberOfLosses = number;
    }

    public void setNumberOfTies(int number) {
        numberOfTies = number;
    }

    public void setNumberOfWins(int number) {
        numberOfWins = number;
    }
}
