package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for the PokemonTeam class
class PokemonTeamTest {

    private PokemonTeam testPokemonTeam;
    private Pokemon testPokemon1;
    private Pokemon testPokemon2;
    private Pokemon testPokemon3;
    private JSONObject testJsonObject;

    @BeforeEach
    void runBefore() {
        testPokemonTeam = new PokemonTeam("Team 1");
        testPokemon1 = new Pokemon("Pikachu", 100);
        testPokemon2 = new Pokemon("Charmander", 200);
        testPokemon3 = new Pokemon("Bulbasaur", 300);
        testJsonObject = new JSONObject();
        testJsonObject.put("team name", testPokemonTeam.getTeamName());
        testJsonObject.put("pokemons", testPokemonTeam.getListOfPokemons());
        testJsonObject.put("number of wins", testPokemonTeam.getNumberOfWins());
        testJsonObject.put("number of losses", testPokemonTeam.getNumberOfLosses());
        testJsonObject.put("number of ties", testPokemonTeam.getNumberOfTies());
    }

    @Test
    void testConstructor() {
        assertEquals("Team 1", testPokemonTeam.getTeamName());
        ArrayList<Pokemon> testTeam = testPokemonTeam.getListOfPokemons();
        assertEquals(0, testTeam.size());
        assertEquals(0, testPokemonTeam.getNumberOfWins());
        assertEquals(0, testPokemonTeam.getNumberOfTies());
        assertEquals(0, testPokemonTeam.getNumberOfLosses());
    }

    @Test
    void testAddPokemon() {
        testPokemonTeam.addPokemon(testPokemon1);
        ArrayList<Pokemon> testTeam = testPokemonTeam.getListOfPokemons();
        assertTrue(testTeam.contains(testPokemon1));
        assertEquals(1, testTeam.size());

        testPokemonTeam.addPokemon(testPokemon2);
        testPokemonTeam.addPokemon(testPokemon3);
        assertTrue(testTeam.contains(testPokemon2));
        assertTrue(testTeam.contains(testPokemon3));
        assertEquals(3, testTeam.size());
    }

    @Test
    void testRemovePokemon() {
        testPokemonTeam.addPokemon(testPokemon1);
        ArrayList<Pokemon> testTeam = testPokemonTeam.getListOfPokemons();
        testPokemonTeam.removePokemon(testPokemon1);
        assertFalse(testTeam.contains(testPokemon1));
        assertEquals(0, testTeam.size());

        testPokemonTeam.addPokemon(testPokemon2);
        testPokemonTeam.addPokemon(testPokemon3);
        testPokemonTeam.removePokemon(testPokemon2);
        assertFalse(testTeam.contains(testPokemon2));
        assertEquals(1, testTeam.size());
    }

    @Test
    void testGetAllPokemonNames() {
        testPokemonTeam.addPokemon(testPokemon1);
        ArrayList<String> pokemonNames = testPokemonTeam.getAllPokemonNames();
        assertEquals(1, pokemonNames.size());
        assertTrue(pokemonNames.contains("Pikachu"));

        testPokemonTeam.addPokemon(testPokemon2);
        testPokemonTeam.addPokemon(testPokemon3);
        pokemonNames = testPokemonTeam.getAllPokemonNames();
        assertEquals(3, pokemonNames.size());
        assertTrue(pokemonNames.contains("Pikachu"));
        assertTrue(pokemonNames.contains("Charmander"));
        assertTrue(pokemonNames.contains("Bulbasaur"));

        testPokemonTeam.removePokemon(testPokemon1);
        testPokemonTeam.removePokemon(testPokemon2);
        testPokemonTeam.removePokemon(testPokemon3);
        pokemonNames = testPokemonTeam.getAllPokemonNames();
        assertTrue(pokemonNames.isEmpty());
    }

    @Test
    void testAddOneToWins() {
        testPokemonTeam.addOneToWins();
        assertEquals(1, testPokemonTeam.getNumberOfWins());

        testPokemonTeam.addOneToWins();
        testPokemonTeam.addOneToWins();
        assertEquals(3, testPokemonTeam.getNumberOfWins());
    }

    @Test
    void testAddOneToTies() {
        testPokemonTeam.addOneToTies();
        assertEquals(1, testPokemonTeam.getNumberOfTies());

        testPokemonTeam.addOneToTies();
        testPokemonTeam.addOneToTies();
        assertEquals(3, testPokemonTeam.getNumberOfTies());
    }

    @Test
    void testAddOneToLosses() {
        testPokemonTeam.addOneToLosses();
        assertEquals(1, testPokemonTeam.getNumberOfLosses());

        testPokemonTeam.addOneToLosses();
        testPokemonTeam.addOneToLosses();
        assertEquals(3, testPokemonTeam.getNumberOfLosses());
    }

    @Test
    void testToJson() {
        testPokemonTeam.toJson();
        assertEquals(testPokemonTeam.getTeamName(), testJsonObject.get("team name"));
        JSONArray testJsonArray = testPokemonTeam.listOfPokemonsToJson();
        assertTrue(testJsonArray.isEmpty());
        assertEquals(0, testJsonArray.length());
        assertEquals(testPokemonTeam.getNumberOfWins(), testJsonObject.get("number of wins"));
        assertEquals(testPokemonTeam.getNumberOfTies(), testJsonObject.get("number of ties"));
        assertEquals(testPokemonTeam.getNumberOfLosses(), testJsonObject.get("number of losses"));

        testPokemonTeam.addPokemon(testPokemon1);
        testPokemonTeam.addPokemon(testPokemon2);
        testPokemonTeam.addPokemon(testPokemon3);
        testPokemonTeam.toJson();
        testJsonArray = testPokemonTeam.listOfPokemonsToJson();
        assertFalse(testJsonArray.isEmpty());
        assertEquals(3, testJsonArray.length());
    }

    @Test
    void testListOfPokemonsToJson() {
        testPokemonTeam.addPokemon(testPokemon1);
        testPokemonTeam.addPokemon(testPokemon2);
        testPokemonTeam.addPokemon(testPokemon3);
        JSONArray testJsonArray = testPokemonTeam.listOfPokemonsToJson();
        assertEquals(3, testJsonArray.length());
    }

    @Test
    void testSetWins() {
        testPokemonTeam.setNumberOfWins(10);
        assertEquals(10, testPokemonTeam.getNumberOfWins());

        testPokemonTeam.setNumberOfWins(35);
        testPokemonTeam.setNumberOfWins(90);
        assertEquals(90, testPokemonTeam.getNumberOfWins());
    }

    @Test
    void testSetTies() {
        testPokemonTeam.setNumberOfTies(20);
        assertEquals(20, testPokemonTeam.getNumberOfTies());

        testPokemonTeam.setNumberOfTies(45);
        testPokemonTeam.setNumberOfTies(100);
        assertEquals(100, testPokemonTeam.getNumberOfTies());
    }

    @Test
    void testSetLosses() {
        testPokemonTeam.setNumberOfLosses(30);
        assertEquals(30, testPokemonTeam.getNumberOfLosses());

        testPokemonTeam.setNumberOfLosses(55);
        testPokemonTeam.setNumberOfLosses(110);
        assertEquals(110, testPokemonTeam.getNumberOfLosses());
    }
}
