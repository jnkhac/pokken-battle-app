package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for the Pokemon class
class PokemonTest {
    private Pokemon testPokemon1;
    private Pokemon testPokemon2;
    private JSONObject testJsonObject;

    @BeforeEach
    void runBefore() {
        testPokemon1 = new Pokemon("Pikachu", 100);
        testPokemon2 = new Pokemon("Charmander", 1);
        testJsonObject = new JSONObject();
        testJsonObject.put("name", testPokemon1.getName());
        testJsonObject.put("health", testPokemon1.getHealth());
    }

    @Test
    void testConstructor() {
        assertEquals("Pikachu", testPokemon1.getName());
        assertEquals(100, testPokemon1.getHealth());
        assertNotNull(testPokemon1.getTackle());
        assertEquals("Tackle", testPokemon1.getTackle().getAttackMoveName());
        assertEquals(10, testPokemon1.getTackle().getAttackMoveDamage());

        assertEquals("Charmander", testPokemon2.getName());
        assertEquals(1, testPokemon2.getHealth());
        assertNotNull(testPokemon2.getTackle());
        assertEquals("Tackle", testPokemon2.getTackle().getAttackMoveName());
        assertEquals(10, testPokemon2.getTackle().getAttackMoveDamage());
    }

    @Test
    void testToJson() {
        testPokemon1.toJson();
        assertEquals(testPokemon1.getName(), testJsonObject.get("name"));
        assertEquals(testPokemon1.getHealth(), testJsonObject.get("health"));
    }

    @Test
    void testSetHealth() {
        testPokemon1.setHealth(1);
        assertEquals(1, testPokemon1.getHealth());

        testPokemon1.setHealth(7);
        testPokemon1.setHealth(1000);
        assertEquals(1000, testPokemon1.getHealth());
    }
}