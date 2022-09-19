package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for the PokemonBattle class
class PokemonBattleTest {
    private PokemonBattle testPokemonBattle1;
    private PokemonBattle testPokemonBattle2;
    private Pokemon testPokemon1;
    private Pokemon testPokemon2;
    private Pokemon testPokemon3;
    private Pokemon testPokemon4;
    private Pokemon testEnemyPokemon1;
    private Pokemon testEnemyPokemon2;

    @BeforeEach
    void runBefore() {
        testPokemon1 = new Pokemon("Pikachu", 100);
        testPokemon2 = new Pokemon("Charmander", 200);
        testPokemon3 = new Pokemon("Bulbasaur", 300);
        testPokemon4 = new Pokemon("Squirtle", 1);
        testEnemyPokemon1 = new Pokemon("Zigzagoon", 400);
        testEnemyPokemon2 = new Pokemon("Palkia",1);
        testPokemonBattle1 = new PokemonBattle(testPokemon1, testEnemyPokemon1);
        testPokemonBattle2 = new PokemonBattle(testPokemon4, testEnemyPokemon2);
    }

    @Test
    void testConstructor() {
        assertEquals(testPokemon1, testPokemonBattle1.getChosenPokemonOnField());
        assertEquals(testEnemyPokemon1, testPokemonBattle1.getEnemyPokemon());
    }

    @Test
    void testAttackEnemyPokemon() {
        testPokemonBattle1.attackEnemyPokemon();
        assertEquals(390, testEnemyPokemon1.getHealth());
        testPokemonBattle2.attackEnemyPokemon();
        assertEquals(-9,testEnemyPokemon2.getHealth());

        testPokemonBattle1.attackEnemyPokemon();
        testPokemonBattle1.attackEnemyPokemon();
        assertEquals(370, testEnemyPokemon1.getHealth());
    }

    @Test
    void testEnemyAttackUser() {
        testPokemonBattle1.enemyAttackUser();
        assertEquals(90, testPokemon1.getHealth());
        testPokemonBattle2.enemyAttackUser();
        assertEquals(-9,testPokemon4.getHealth());

        testPokemonBattle1.enemyAttackUser();
        testPokemonBattle1.enemyAttackUser();
        assertEquals(70, testPokemon1.getHealth());
    }

    @Test
    void testSetChosenPokemonOnTeam() {
        testPokemonBattle1.setChosenPokemonOnField(testPokemon2);
        assertEquals(testPokemon2, testPokemonBattle1.getChosenPokemonOnField());

        testPokemonBattle1.setChosenPokemonOnField(testPokemon1);
        testPokemonBattle1.setChosenPokemonOnField(testPokemon3);
        assertEquals(testPokemon3, testPokemonBattle1.getChosenPokemonOnField());
    }
}
