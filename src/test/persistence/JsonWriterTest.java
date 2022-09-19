package persistence;

import model.Pokemon;
import model.PokemonTeam;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CODE REFERENCES:
// JsonSerializationDemo - JsonWriterTest
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a test suite for the JsonWriter class
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            JsonWriter writer = new JsonWriter("./data/my\0invalid:fileName.json");
            writer.openWriter();
            fail("IOException expected to be thrown - there existed a file that was writable");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyPokemonTeamZeroScore() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPokemonTeamZeroScore.json");
            writer.openWriter();
            writer.write(pt);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPokemonTeamZeroScore.json");
            pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(0, pt.getListOfPokemons().size());
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(0, pt.getNumberOfTies());
            assertEquals(0, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("Exception was thrown - did not expect it to be thrown");
        }
    }

    @Test
    void testWriterEmptyPokemonTeamNonZeroScore() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            pt.setNumberOfLosses(30);
            pt.setNumberOfTies(25);
            pt.setNumberOfWins(76);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPokemonTeamNonZeroScore.json");
            writer.openWriter();
            writer.write(pt);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPokemonTeamNonZeroScore.json");
            pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(0, pt.getListOfPokemons().size());
            assertEquals(30, pt.getNumberOfLosses());
            assertEquals(25, pt.getNumberOfTies());
            assertEquals(76, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("Exception was thrown - did not expect it to be thrown");
        }
    }

    @Test
    void testWriterEmptyPokemonTeamMixedScore() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            pt.setNumberOfLosses(0);
            pt.setNumberOfTies(0);
            pt.setNumberOfWins(1);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPokemonTeamMixedScore.json");
            writer.openWriter();
            writer.write(pt);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPokemonTeamMixedScore.json");
            pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(0, pt.getListOfPokemons().size());
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(0, pt.getNumberOfTies());
            assertEquals(1, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("Exception was thrown - did not expect it to be thrown");
        }
    }

    @Test
    void testWriterGeneralPokemonTeamZeroScore() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            Pokemon charmander = new Pokemon("Charmander", 100);
            Pokemon bulbasaur = new Pokemon("Bulbasaur", 100);
            Pokemon squirtle = new Pokemon("Squirtle", 100);
            pt.addPokemon(charmander);
            pt.addPokemon(bulbasaur);
            pt.addPokemon(squirtle);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPokemonTeamZeroScore.json");
            writer.openWriter();
            writer.write(pt);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPokemonTeamZeroScore.json");
            pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(3, pt.getListOfPokemons().size());
            checkPokemon("Charmander", 100, pt.getListOfPokemons().get(0));
            checkPokemon("Bulbasaur", 100, pt.getListOfPokemons().get(1));
            checkPokemon("Squirtle", 100, pt.getListOfPokemons().get(2));
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(0, pt.getNumberOfTies());
            assertEquals(0, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("Exception was thrown - did not expect it to be thrown");
        }
    }

    @Test
    void testWriterGeneralPokemonTeamNonZeroScore() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            pt.setNumberOfLosses(79);
            pt.setNumberOfTies(23);
            pt.setNumberOfWins(600);
            Pokemon chikorita = new Pokemon("Chikorita", 100);
            Pokemon cyndaquil = new Pokemon("Cyndaquil", 100);
            Pokemon totodile = new Pokemon("Totodile", 100);
            pt.addPokemon(chikorita);
            pt.addPokemon(cyndaquil);
            pt.addPokemon(totodile);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPokemonTeamNonZeroScore.json");
            writer.openWriter();
            writer.write(pt);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPokemonTeamNonZeroScore.json");
            pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(3, pt.getListOfPokemons().size());
            checkPokemon("Chikorita", 100, pt.getListOfPokemons().get(0));
            checkPokemon("Cyndaquil", 100, pt.getListOfPokemons().get(1));
            checkPokemon("Totodile", 100, pt.getListOfPokemons().get(2));
            assertEquals(79, pt.getNumberOfLosses());
            assertEquals(23, pt.getNumberOfTies());
            assertEquals(600, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("Exception was thrown - did not expect it to be thrown");
        }
    }

    @Test
    void testWriterGeneralPokemonTeamMixedScore() {
        try {
            PokemonTeam pt = new PokemonTeam("My Pokemon Team");
            pt.setNumberOfLosses(0);
            pt.setNumberOfTies(1);
            pt.setNumberOfWins(1);
            Pokemon chikorita = new Pokemon("Chikorita", 100);
            Pokemon cyndaquil = new Pokemon("Cyndaquil", 100);
            Pokemon totodile = new Pokemon("Totodile", 100);
            pt.addPokemon(chikorita);
            pt.addPokemon(cyndaquil);
            pt.addPokemon(totodile);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPokemonTeamMixedScore.json");
            writer.openWriter();
            writer.write(pt);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPokemonTeamMixedScore.json");
            pt = reader.read();
            assertEquals("My Pokemon Team", pt.getTeamName());
            assertEquals(3, pt.getListOfPokemons().size());
            checkPokemon("Chikorita", 100, pt.getListOfPokemons().get(0));
            checkPokemon("Cyndaquil", 100, pt.getListOfPokemons().get(1));
            checkPokemon("Totodile", 100, pt.getListOfPokemons().get(2));
            assertEquals(0, pt.getNumberOfLosses());
            assertEquals(1, pt.getNumberOfTies());
            assertEquals(1, pt.getNumberOfWins());
        } catch (IOException e) {
            fail("Exception was thrown - did not expect it to be thrown");
        }
    }
}
