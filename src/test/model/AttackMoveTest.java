package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for the AttackMove class
class AttackMoveTest {
    private AttackMove testAttackMove1;
    private AttackMove testAttackMove2;

    @BeforeEach
    void runBefore() {
        testAttackMove1 = new AttackMove("Tackle", 10);
        testAttackMove2 = new AttackMove("Protect", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Tackle", testAttackMove1.getAttackMoveName());
        assertEquals(10, testAttackMove1.getAttackMoveDamage());
        assertEquals("Protect", testAttackMove2.getAttackMoveName());
        assertEquals(0, testAttackMove2.getAttackMoveDamage());
    }
}
