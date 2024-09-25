package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameCharacterTest {


    @Test
    public void testGetName() {
        GameCharacter hero = new NPC("Skeleton", 100, 1.2, 0);
        assertEquals("Skeleton", hero.getName());
    }


    @Test
    public void testGetHitPoints() {
        GameCharacter hero = new NPC("Skeleton", 100, 1.2, 0);
        assertEquals(100, hero.getHitPoints());
    }

    @Test
    public void testTakeDamage() {
        GameCharacter hero = new NPC("Skeleton", 100, 1.2, 0);
        hero.takeDamage(30);
        assertEquals(70, hero.getHitPoints());
        hero.takeDamage(100);
        assertEquals(0, hero.getHitPoints());
    }


    @Test
    public void testAttackMiss() {
        GameCharacter hero = new NPC("Skeleton", 100, 1.2, 0);
        GameCharacter badGuy = new NPC("Goblin", 50, 1.2, 0);
        hero.equippedWeapon = new Weapon("Spear", 40); 

        int missedAttacks = 0;
        for (int i = 0; i < 10; i++) {
            if (hero.attack(badGuy) == 0) {
                missedAttacks++;
            }
        }
        assertTrue(missedAttacks > 0);
    }


    @Test
    public void testAttackHit() {
        GameCharacter hero = new NPC("Skeleton", 100, 1.2, 0);
        GameCharacter defender = new NPC("Goblin", 50, 0.8, 0);
        hero.equippedWeapon = new Weapon("Spear", 40);

        boolean gotAHit = false;
        int damageDone = 0;
        for (int i = 0; i < 10; i++) {
            damageDone = hero.attack(defender);
            if (damageDone > 0) {
                gotAHit = true;
                break;
            }
        }
        assertTrue(gotAHit);
        assertTrue(damageDone >= 1 && damageDone <= hero.getEquippedWeapon().getDamage());
        assertTrue(defender.getHitPoints() < 50);
    }
}
