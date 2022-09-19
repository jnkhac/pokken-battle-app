package persistence;

import model.Pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CODE REFERENCES:
// JsonSerializationDemo - JsonTest
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a helper for other Json test classes
public class JsonTest {
    protected void checkPokemon(String name, int health, Pokemon pokemon) {
        assertEquals(name, pokemon.getName());
        assertEquals(health, pokemon.getHealth());
    }
}
