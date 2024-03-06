package org.example;

// Import statements for static methods
import static org.example.RandomUtils.generateDeathChance;

public class Main {

    public static void main(String[] args) {
        // The main method where the game starts
        String saveFile = "hero.save"; // File name for saving the game state
        boolean NPCdead; // Flag to check if the NPC is dead

        Player hero; // Declare a variable to hold the player object
        try {
            // Attempt to load the player object from a save file
            hero = (Player) FileUtils.loadObject(saveFile);
            // Ask the player if they want to continue from the previous save
            if (Inputs.promptYesNo("Do you want to continue from previous save?\n")) {
                System.out.println("Loading save file...\n");
            } else {
                // If the player chooses not to continue, create a new player
                hero = Player.spawnPlayer();
            }
        } catch (RuntimeException e) {
            // Catch block to handle errors, such as missing save file
            System.out.println("No save file found. Creating new hero.\n");
            hero = Player.spawnPlayer(); // Create a new player
        }

        // Display player status
        System.out.printf("You are %s with %d HP and %s (%d) equipped. And you have %d health potions with you.%n",
                hero.getName(),
                hero.getHitPoints(),
                hero.getEquippedWeapon().getName(),
                hero.getEquippedWeapon().getDamage(),
                hero.getPotion());

        // Game loop starts here
        while (true) {
            NPCdead = false; // Reset NPCdead flag for each loop iteration
            NPC enemy = NPC.spawnNpc(); // Spawn a new NPC enemy
            // Display information about the encountered enemy
            System.out.printf("A scary-looking %s runs towards you wielding %s (%d).%n",
                    enemy.getName(),
                    enemy.getEquippedWeapon(),
                    enemy.getEquippedWeapon().getDamage());

            // Ask the player if they want to attack the encountered enemy
            if (!Inputs.promptYesNo("Do you want to attack?")) {
                // If the player chooses not to attack, determine their fate
                if (generateDeathChance()) {
                    System.out.println("You ran away and survived.");
                    FileUtils.saveObject(hero, saveFile); // Save the game state
                    System.exit(0); // Exit the game
                } else {
                    // If running away fails, the player dies
                    System.out.println("It attacked you in the back as you ran away like a coward, YOU DEAD BRAH!");
                    Inputs.closeScanner(); // Close the scanner resource
                    FileUtils.deleteFile(saveFile); // Delete the save file
                    System.exit(0); // Exit the game
                }
            } else {
                // If the player chooses to attack, start the battle sequence
                System.out.println("LET THE BATTLE BEGIN!");
                String battleChoice;
                // Battle loop continues as long as both the hero and enemy have HP
                while (hero.getHitPoints() > 0 && enemy.getHitPoints() > 0) {
                    // Prompt player for battle action: attack, check inventory, or flee
                    battleChoice = Inputs.promptCommand("\nAttack (Enter), check your inventory (i) or flee (q)?", "", "i", "q");

                    if ("i".equals(battleChoice)) {
                        // If the player chooses to check inventory, handle inventory actions
                        String inventoryChoice = Inputs.promptCommand("Do you want to delete a weapon from your inventory? (w), or use a health potion (p)?", "w", "p");
                        if ("p".equals(inventoryChoice)) {
                            // If the player chooses to use a potion, heal the player if potions are available
                            if (hero.getPotion() > 0) {
                                hero.heal(RandomUtils.generateRandomHealAmount());
                                System.out.printf("You have %d health potions left. You have %d HP left.%n",
                                        hero.getPotion(),
                                        hero.getHitPoints());
                            } else {
                                System.out.println("You have no health potions left.");
                            }
                        }
                        if ("w".equals(inventoryChoice)) {
                            // If the player chooses to delete a weapon, display inventory and handle deletion
                            for (int i = 0; i < hero.getInventory().size(); i++) {
                                System.out.printf("%d %s. (%d)\n",
                                        i + 1,
                                        hero.getInventory().get(i).getName(),
                                        hero.getInventory().get(i).getDamage());
                            }
                            int deleteChoice = Inputs.promptForInt("Enter the number of the weapon to delete, or 0 to keep all: ") - 1;
                            if (deleteChoice >= 0 && deleteChoice < hero.getInventory().size()) {
                                Weapon removedWeapon = hero.getInventory().remove(deleteChoice);
                                System.out.println(removedWeapon.getName() + " was removed from your inventory.");
                            } else if (deleteChoice == -1) {
                                System.out.println("Keeping all weapons.");
                            } else {
                                System.out.println("Invalid choice. No weapons were removed from your inventory.");
                            }
                        } else {
                            System.out.println("No changes made to your inventory.");
                        }
                    } else if ("q".equals(battleChoice)) {
                        // If the player chooses to flee, end the game with the player's death
                        System.out.println("You decide to make a run for it. It attacked you in the back as you ran away like a coward. YOU DEAD BRAH!");
                        Inputs.closeScanner(); // Close the scanner resource
                        FileUtils.deleteFile(saveFile); // Delete the save file
                        System.exit(0); // Exit the game
                    } else {
                        // Handle the attack sequence if the player chooses to attack
                        System.out.println("Choose a weapon from your inventory to attack with:");
                        for (int i = 0; i < hero.getInventory().size(); i++) {
                            System.out.printf("%d. %s (%d)%n",
                                    i + 1,
                                    hero.getInventory().get(i).getName(),
                                    hero.getInventory().get(i).getDamage());
                        }
                        int weaponChoice = Inputs.promptForInt("Select a weapon number: ") - 1;
                        if (weaponChoice >= 0 && weaponChoice < hero.getInventory().size()) {
                            hero.equipWeapon(hero.getInventory().get(weaponChoice));
                        } else {
                            System.out.println("Invalid choice, using equipped weapon.");
                        }

                        int heroDamage = hero.attack(enemy);
                        if (heroDamage > 0) {
                            System.out.printf("You hit %s with %s for %d HP!\n",
                                    enemy.getName(),
                                    hero.getEquippedWeapon().getName(),
                                    heroDamage);
                        } else {
                            System.out.println("You missed!");
                        }

                        if (enemy.getHitPoints() <= 0) {
                            NPCdead = true; // Mark the NPC as dead if its HP drops to 0 or below
                            break; // Exit the battle loop if the NPC is dead
                        }

                        // Enemy attacks
                        int enemyDamage = enemy.attack(hero);
                        if (enemyDamage > 0) {
                            System.out.printf("%s hits you with %s for %d HP!%n",
                                    enemy.getName(),
                                    enemy.getEquippedWeapon().getName(),
                                    enemyDamage);
                        } else {
                            System.out.printf("%s's attack missed!\n",
                                    enemy.getName());
                        }

                        // Display the current HP of both the hero and the enemy
                        System.out.printf("You have %d HP left.\nAnd the %s has %d HP left.\n",
                                hero.getHitPoints(),
                                enemy.getName(),
                                enemy.getHitPoints());

                        if (hero.getHitPoints() <= 0) {
                            // If the hero's HP drops to 0 or below, end the game with the player's death
                            System.out.println("You are dead. Better luck next time!");
                            Inputs.closeScanner(); // Close the scanner resource
                            FileUtils.deleteFile(saveFile); // Delete the save file
                            System.exit(0); // Exit the game
                        }
                    }
                }

                if (NPCdead) {
                    // If the NPC is dead, display victory message and loot options
                    System.out.printf("%s is dead. You win with %d health left!%n",
                            enemy.getName(),
                            hero.getHitPoints());
                    System.out.printf("the %s dropped: %s (%d)\nand\n%d potions?\n",
                            enemy.getName(),
                            enemy.getEquippedWeapon().getName(),
                            enemy.getEquippedWeapon().getDamage(),
                            enemy.getPotion());
                    boolean pickUpWeapon = Inputs.promptYesNo("Do you want to pick up the weapon?");

                    if (pickUpWeapon) {
                        // If the player chooses to pick up the weapon, add it to their inventory
                        hero.addWeaponToInventory(enemy.getEquippedWeapon());
                        hero.addPotion(enemy.getPotion()); // Also add any dropped potions
                        System.out.printf("%s added to your inventory and %d potions.\n",
                                enemy.getEquippedWeapon().getName(),
                                enemy.getPotion());
                    }
                }

                if (!Inputs.promptYesNo("Would you like to continue adventuring?")) {
                    System.out.println("Your adventure ends here."); // End the game if the player chooses to stop
                    Inputs.closeScanner(); // Close the scanner resource
                    break; // Break out of the game loop
                }
            }
        }
        FileUtils.saveObject(hero, saveFile); // Save the game state before exiting
    }
}
