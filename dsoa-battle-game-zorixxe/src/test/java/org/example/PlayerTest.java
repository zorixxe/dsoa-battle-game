package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    // Test if the Player constructor sets everything up right
    @Test
    public void testConstructor() {
        Player player = new Player("pruttipong", 100, 0.8, RandomUtils.generateRandomPotion());
        assertEquals("pruttipong", player.getName());
        assertEquals(100, player.getHitPoints());
        assertEquals(0.8, player.getDexterity());
        assertEquals("Sword", player.getEquippedWeapon().getName());
    }


    @Test
    public void testTakeDamage() {
        Player player = new Player("pruttipong", 100, 0.5, RandomUtils.generateRandomPotion());
        player.takeDamage(30);
        assertEquals(70, player.getHitPoints());
        player.takeDamage(100);
        assertEquals(0, player.getHitPoints());
    }


    @Test
    public void testAttackMiss() {
        Player player = new Player("pruttipong", 100, 0.5, RandomUtils.generateRandomPotion());
        GameCharacter enemy = new NPC("goblin", 50, 1.2, 0);
        player.equippedWeapon = new Weapon("Stick", 5);

        int misses = 0;
        for (int i = 0; i < 10; i++) {
            if (player.attack(enemy) == 0) {
                misses++;
            }
        }
        assertTrue(misses > 0);
    }


    @Test
    public void testAttackHit() {
        Player player = new Player("pruttipong", 100, 0.5, RandomUtils.generateRandomPotion());
        GameCharacter enemy = new NPC("goblin", 50, 0.8, 0);
        player.equippedWeapon = new Weapon("Stick", 5);

        boolean gotAHit = false;
        int totalDamage = 0;
        for (int i = 0; i < 10; i++) {
            int damage = player.attack(enemy);
            if (damage > 0) {
                gotAHit = true;
                totalDamage += damage;
            }
        }
        assertTrue(gotAHit);
        assertTrue(totalDamage > 0);
        assertTrue(enemy.getHitPoints() < 50);
    }
}
