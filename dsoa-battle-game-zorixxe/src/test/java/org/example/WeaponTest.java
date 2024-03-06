package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    @Test
    void testGetName() {
        Weapon weapon = new Weapon("Sword", 10);
        assertEquals("Sword", weapon.getName());
    }

    @Test
    void testSetName() {
        Weapon weapon = new Weapon("Axe", 15);
        weapon.setName("Hammer");
        assertEquals("Hammer", weapon.getName());
    }

    @Test
    void testGetDamage() {
        Weapon weapon = new Weapon("Bow", 5);
        assertEquals(5, weapon.getDamage());
    }
    @Test
    void testSetDamage() {
        Weapon weapon = new Weapon("Staff", 8);
        weapon.setDamage(12);
        assertEquals(12, weapon.getDamage());
    }
}
