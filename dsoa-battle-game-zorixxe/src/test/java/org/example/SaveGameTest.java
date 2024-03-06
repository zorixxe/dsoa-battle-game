package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.File;

public class SaveGameTest {

    @Test
    public void testSaveAndLoadGame() {
        String saveFile = "testHero.save";

        String expectedName = "Test Hero";
        int hitPoints = 100;
        double dexterity = 1.0;
        int potionCount = 5;
        Player originalHero = new Player(expectedName, hitPoints, dexterity, potionCount);


        Weapon weapon = new Weapon("Sword", 10);
        originalHero.equipWeapon(weapon);

        try {
            FileUtils.saveObject(originalHero, saveFile);
            Player loadedHero = (Player) FileUtils.loadObject(saveFile);

            assertEquals(originalHero.getName(), loadedHero.getName(), "Player names should match");
            assertEquals(originalHero.getEquippedWeapon().getName(), loadedHero.getEquippedWeapon().getName(), "Player weapon names should match");
        } finally {
            new File(saveFile).delete();
        }
    }
}
