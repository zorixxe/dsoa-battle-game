package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class GameCharacter implements Serializable {
    private int potion;
    private String name;
    private int hitPoints;
    protected Weapon equippedWeapon;
    protected double dexterity; // How nimble they are, affects hitting and dodging
    protected ArrayList<Weapon> inventory;

    // Constructor to set everything up when a new character is created
    public GameCharacter(String name, int hitPoints, double dexterity, int potion) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.dexterity = dexterity;
        this.potion = potion;
        this.inventory = new ArrayList<>();
    }

    // When they get hit, we reduce their HP.
    public void takeDamage(int damage) {
        this.hitPoints -= damage;
        if (this.hitPoints < 0) {
            this.hitPoints = 0;
        }
    }

    // Healing up with potions. Can't go above 100 HP
    public void heal(int healAmount) {
        this.hitPoints += healAmount;
        if (this.hitPoints > 100) {
            this.hitPoints = 100;
        }
        potion--; // Used up a potion
    }

    // Got a potion? Add it here.
    public void addPotion(int potion) {
        this.potion += potion;
    }

    // Trying to hit another character. Whether it hits or misses depends on dexterity and a bit of luck.
    public int attack(GameCharacter defender) {
        Random random = new Random(); // For that randomness

        double hitChance;
        if (defender.dexterity == 1.2) {
            hitChance = 0.8;
        } else if (defender.dexterity == 0.8) {
            hitChance = 0.66;
        } else {
            hitChance = 0.5;
        }

        if (random.nextDouble() > hitChance) {
            return 0; // Missed the hit
        }

        // Damage calculation, based on the weapon and some RNG
        int weaponDamage = this.equippedWeapon.getDamage();
        int minDamage = (int) (weaponDamage * this.dexterity);

        minDamage = Math.min(minDamage, weaponDamage - 1); // Can't do more damage than the weapon max, minus 1

        int damageRange = weaponDamage - minDamage; // Range of possible damage
        int damage = (damageRange > 0) ? random.nextInt(damageRange + 1) + minDamage : minDamage; // Calculate that damage
        defender.takeDamage(damage); // Apply damage to the defender
        return damage;
    }

    // Add a new weapon to the inventory.
    public void addWeaponToInventory(Weapon weapon) {
        this.inventory.add(weapon);
    }


    public boolean removeWeaponFromInventory(int index) {
        if (index >= 0 && index < this.inventory.size()) {
            this.inventory.remove(index);
            return true; // Successfully removed
        } else {
            return false; // Oops, can't remove that
        }
    }


    public ArrayList<Weapon> getInventory() {
        return this.inventory;
    }


    public String getName() {
        return name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public int getPotion() {
        return potion;
    }
}