package model;

import org.json.JSONObject;
import persistence.Writable;

// CODE REFERENCES:
// JsonSerializationDemo - Thingy
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a pokemon with a name, amount of health, and an attack move.
public class Pokemon implements Writable {
    private String name;
    private int health;
    private AttackMove tackle;

    // REQUIRES: given name must be non-zero in length
    //           given health must be >= 1
    // EFFECTS: constructs a pokemon with given name, health, and attack move called "Tackle"
    public Pokemon(String name, int health) {
        this.name = name;
        this.health = health;
        tackle = new AttackMove("Tackle", 10);
    }

    // EFFECTS: returns this (Pokemon) as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("health", health);
        return json;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public AttackMove getTackle() {
        return tackle;
    }
}
