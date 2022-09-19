package model;

// Represents an attack move having a name and damage amount.
public class AttackMove {
    private String attackMoveName;
    private int attackMoveDamage;

    // REQUIRES: given attackMoveName must be non-zero in length
    //           given attackMoveDamage must be >= 0
    // EFFECTS: constructs an attack move with the given name and damage
    public AttackMove(String attackMoveName, int attackMoveDamage) {
        this.attackMoveName = attackMoveName;
        this.attackMoveDamage = attackMoveDamage;
    }

    public String getAttackMoveName() {
        return attackMoveName;
    }

    public int getAttackMoveDamage() {
        return attackMoveDamage;
    }
}
