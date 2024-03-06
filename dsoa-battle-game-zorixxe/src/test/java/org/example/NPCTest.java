package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NPCTest {


    @Test
    public void testGetName() {
        GameCharacter npc = new NPC("goblin", 50, 0.8, 0);
        assertEquals("goblin", npc.getName());
    }


    @Test
    public void testGetHitPoints() {
        GameCharacter npc = new NPC("goblin", 50, 0.8, 0);
        assertEquals(50, npc.getHitPoints());
    }


    @Test
    public void testTakeDamage() {
        GameCharacter npc = new NPC("goblin", 50, 0.8, 0);
        npc.takeDamage(20);
        assertEquals(30, npc.getHitPoints());
        npc.takeDamage(100);
        assertEquals(0, npc.getHitPoints());
    }

    @Test
    public void testAttackMiss() {
        GameCharacter npc = new NPC("goblin", 50, 0.8, 0);
        GameCharacter enemy = new Player("pruttipong", 100, 1.2, RandomUtils.generateRandomPotion());
        npc.equippedWeapon = new Weapon("Club", 25);

        int misses = 0;
        for (int i = 0; i < 10; i++) {
            if (npc.attack(enemy) == 0) {
                misses++;
            }
        }
        assertTrue(misses > 0);
    }

    @Test
    public void testAttackHit() {
        GameCharacter npc = new NPC("goblin", 50, 0.8, 0);
        GameCharacter enemy = new Player("pruttipong", 100, 0.5, RandomUtils.generateRandomPotion());
        npc.equippedWeapon = new Weapon("Club", 25);

        boolean hitRegistered = false;
        int totalDamage = 0;
        for (int i = 0; i < 10; i++) {
            int damage = npc.attack(enemy);
            if (damage > 0) {
                hitRegistered = true;
                totalDamage += damage;
            }
        }

        assertTrue(hitRegistered);
        assertTrue(totalDamage >= 0 && totalDamage <= npc.getEquippedWeapon().getDamage() * 10);
        assertTrue(enemy.getHitPoints() < 100);
    }

    @Test
    void spawnNpc() {
        GameCharacter npc = NPC.spawnNpc();
        assertTrue(npc.getName().equals("Ghoul") || npc.getName().equals("Skeleton") || npc.getName().equals("Sundsbo") || npc.getName().equals("Vampire"));
        assertTrue(npc.getHitPoints() >= 100 && npc.getHitPoints() <= 150);
    }
}
