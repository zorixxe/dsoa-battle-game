package org.example;

import java.util.Scanner;

class Player extends GameCharacter {
    // Constructor, sets up the player with a default weapon and other traits
    public Player(String name, int hitPoints, double dexterity, int potion) {
        super(name, hitPoints, dexterity, potion);
        Weapon startingWeapon = new Weapon("Sword", 30); // Default starting weapon
        this.equippedWeapon = startingWeapon;
        this.addWeaponToInventory(startingWeapon); // Add the starting weapon to the inventory
    }

    // Method to change the equipped weapon, based on inventory index
    public void equipWeaponFromInventory(int index) {
        if (index >= 0 && index < this.inventory.size()) {
            this.equippedWeapon = this.inventory.get(index);
            System.out.println("Now equipped with " + this.equippedWeapon.getName() + ".");
        } else {
            System.out.println("Invalid weapon selection."); // Invalid selection
        }
    }

    public double getDexterity() {
        return dexterity; // Getter for dexterity
    }

    public void equipWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon; // Method to set the equipped weapon directly
    }

    // Method to create a new player, including choosing armor which affects dexterity
    public static Player spawnPlayer(){
        double armorChoice = 0; // Initial armor choice, affects dexterity

        System.out.println("What is your name hero?"); // Ask for player name
        Scanner scanner = new Scanner(System.in);
        String heroName = scanner.nextLine(); // Read player name

        while (true) {
            // Armor choice affects dexterity
            System.out.println(
                    "What sort of armor are you wearing?\n1. Light armor\n2. Medium armor\n3. Heavy Armor\nThe lighter the armor the harder you can hit but\nthe more damage you take.");
            armorChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            if (armorChoice >= 1 && armorChoice <= 3) {
                if (armorChoice == 1) {
                    armorChoice = 1.2; // Light armor increases dexterity
                    System.out.println("You have chosen light armor\nDexterity set to " + armorChoice);
                } else if (armorChoice == 2) {
                    armorChoice = 0.8; // Medium armor, balanced
                    System.out.println("You have chosen medium armor\nDexterity set to " + armorChoice);
                } else {
                    armorChoice = 0.5; // Heavy armor reduces dexterity
                    System.out.println("You have chosen heavy armor\nDexterity set to " + armorChoice);
                }
                break;
            } else {
                System.out.println("Not a valid armor class. Please choose 1, 2, or 3."); // Invalid armor choice
            }
        }
        return new Player(heroName, RandomUtils.generateRandomHP(), armorChoice, RandomUtils.generateRandomPotion()); // Return the new player
    }
}