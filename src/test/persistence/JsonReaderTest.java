package persistence;

import model.PokemonTeam;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CODE REFERENCES:
// JsonSerializationDemo - JsonReaderTest
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a test suite for the JsonReader class
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/testReaderNonExistentFile.json");
        try {
            PokemonTeam pt = reader.read();
            fail("IOException expected to be thrown - there existed a file of that name");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyPokemonTeamZeroScore() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPokemonTeamZeroScore.json");
        try {
            PokemonTeam pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(0, pt.getListOfPokemons().size());
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(0, pt.getNumberOfTies());
            assertEquals(0, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("IOException was thrown - expected nothing to be thrown/couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyPokemonTeamNonZeroScore() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPokemonTeamNonZeroScore.json");
        try {
            PokemonTeam pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(0, pt.getListOfPokemons().size());
            assertEquals(100, pt.getNumberOfLosses());
            assertEquals(54, pt.getNumberOfTies());
            assertEquals(33, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("IOException was thrown - expected nothing to be thrown/couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyPokemonTeamMixedScore() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPokemonTeamMixedScore.json");
        try {
            PokemonTeam pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(0, pt.getListOfPokemons().size());
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(1, pt.getNumberOfTies());
            assertEquals(0, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("IOException was thrown - expected nothing to be thrown/couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPokemonTeamZeroScore() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPokemonTeamZeroScore.json");
        try {
            PokemonTeam pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(3, pt.getListOfPokemons().size());
            checkPokemon("Charmander", 100, pt.getListOfPokemons().get(0));
            checkPokemon("Bulbasaur", 100, pt.getListOfPokemons().get(1));
            checkPokemon("Squirtle", 100, pt.getListOfPokemons().get(2));
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(0, pt.getNumberOfTies());
            assertEquals(0, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("IOException was thrown - expected nothing to be thrown/couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPokemonTeamNonZeroScore() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPokemonTeamNonZeroScore.json");
        try {
            PokemonTeam pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(3, pt.getListOfPokemons().size());
            checkPokemon("Chikorita", 100, pt.getListOfPokemons().get(0));
            checkPokemon("Cyndaquil", 100, pt.getListOfPokemons().get(1));
            checkPokemon("Totodile", 100, pt.getListOfPokemons().get(2));
            assertEquals(5, pt.getNumberOfLosses());
            assertEquals(12, pt.getNumberOfTies());
            assertEquals(26, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("IOException was thrown - expected nothing to be thrown/couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPokemonTeamMixedScore() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPokemonTeamMixedScore.json");
        try {
            PokemonTeam pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(3, pt.getListOfPokemons().size());
            checkPokemon("Charmander", 100, pt.getListOfPokemons().get(0));
            checkPokemon("Bulbasaur", 100, pt.getListOfPokemons().get(1));
            checkPokemon("Squirtle", 100, pt.getListOfPokemons().get(2));
            assertEquals(1, pt.getNumberOfLosses());
            assertEquals(0, pt.getNumberOfTies());
            assertEquals(2, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("IOException was thrown - expected nothing to be thrown/couldn't read from file");
        }
    }
}
