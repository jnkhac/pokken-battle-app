package model;

// Represents a pokemon battle having a pokemon on field from the user, and an enemy pokemon
public class PokemonBattle {

    private Pokemon chosenPokemonOnField;
    private Pokemon enemyPokemon;

    // REQUIRES: given chosenPokemonOnField must not be null
    //           given enemyPokemon must not be null
    // EFFECTS: constructs a pokemon battle with given/chosen pokemon on field, and given enemy pokemon
    public PokemonBattle(Pokemon chosenPokemonOnField, Pokemon enemyPokemon) {
        EventLog.getInstance().logEvent(new Event("Started a pokemon battle against: "
                + enemyPokemon.getName()));
        this.chosenPokemonOnField = chosenPokemonOnField;
        this.enemyPokemon = enemyPokemon;
    }

    // REQUIRES: enemyPokemon health must be > 0
    // MODIFIES: this
    // EFFECTS: subtracts the amount of attack move damage from enemyPokemon health
    public void attackEnemyPokemon() {
        EventLog.getInstance().logEvent(new Event(chosenPokemonOnField.getName() + " attacked "
                + enemyPokemon.getName() + " using Tackle."));
        int attackMoveDamage = chosenPokemonOnField.getTackle().getAttackMoveDamage();
        int healthRemaining = enemyPokemon.getHealth() - attackMoveDamage;
        enemyPokemon.setHealth(healthRemaining);
    }

    // REQUIRES: chosenPokemonOnField health must be > 0
    // MODIFIES: this
    // EFFECTS: subtracts the amount of attack move damage from chosenPokemonOnField health
    public void enemyAttackUser() {
        EventLog.getInstance().logEvent(new Event(enemyPokemon.getName() + " attacked "
                + chosenPokemonOnField.getName() + " using Tackle."));
        int attackMoveDamage = enemyPokemon.getTackle().getAttackMoveDamage();
        int healthRemaining = chosenPokemonOnField.getHealth() - attackMoveDamage;
        chosenPokemonOnField.setHealth(healthRemaining);
    }

    public Pokemon getChosenPokemonOnField() {
        return chosenPokemonOnField;
    }

    public Pokemon getEnemyPokemon() {
        return enemyPokemon;
    }

    public void setChosenPokemonOnField(Pokemon chosenPokemonOnField) {
        this.chosenPokemonOnField = chosenPokemonOnField;
        EventLog.getInstance().logEvent(new Event("Switched pokemon to: "
                + chosenPokemonOnField.getName()));
    }
}
