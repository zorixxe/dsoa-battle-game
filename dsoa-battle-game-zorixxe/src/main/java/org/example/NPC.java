package org.example;

import java.util.Random;
class NPC extends GameCharacter {
    private static final String[] NAMES = {"Ghoul", "Skeleton", "Sundsbo", "Vampire"}; // Possible enemy names
    private static Weapon[] WEAPONS = {
            new Weapon("Poleaxe", 30), // Possible enemy weapons
            new Weapon("Axe", 35),
            new Weapon("Club", 25),
            new Weapon("Dagger", 20),
            new Weapon("Pear", 40),
    };

    // Constructor, same as GameCharacter but for enemies
    public NPC(String name, int hitPoints, double dexterity, int potion) {
        super(name, hitPoints, dexterity, potion);
    }

    // Spawns a new enemy with random traits
    public static NPC spawnNpc() {
        Random rand = new Random(); // For randomness
        String name = NAMES[rand.nextInt(NAMES.length)]; // Random name
        Weapon weapon = WEAPONS[rand.nextInt(WEAPONS.length)]; // Random weapon
        int hitPoints = 100 + rand.nextInt(51); // Random HP
        int potion = RandomUtils.generateRandomPotion(); // Random number of potions, only exists to be picked up layer
        NPC npc = new NPC(name, hitPoints, 1.2, potion); // Create the NPC
        npc.equippedWeapon = weapon; // Equip the weapon
        return npc;
    }
    public void setEquippedWeapon(Weapon weapon) {
        this.equippedWeapon = weapon; // Method to set the equipped weapon
    }
}
