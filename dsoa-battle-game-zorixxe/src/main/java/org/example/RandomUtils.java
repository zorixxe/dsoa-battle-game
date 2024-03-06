package org.example;

public class RandomUtils {
    // Generates a random HP value between 100 and 140
    public static int generateRandomHP() {
        return new java.util.Random().nextInt(41) + 100;
    }
    // Generates a random number of potions between 1 and 5
    public static int generateRandomPotion() {
        return new java.util.Random().nextInt(5) + 1;
    }
    // Generates a random heal amount between 10 and 30
    public static int generateRandomHealAmount() {
        return new java.util.Random().nextInt(21) + 10;
    }
    // 50/50 chance of generating true or false, used for escape chances
    public static boolean generateDeathChance() {
        return new java.util.Random().nextBoolean();
    }
}
