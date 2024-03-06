package org.example;

import java.util.Scanner;

public class Inputs {
    private static final Scanner scanner = new Scanner(System.in); // The scanner object for reading input

    // Asking a yes or no question. Keeps asking until it gets a yes or no.
    public static boolean promptYesNo(String message) {
        System.out.printf("%s (yes/no): ", message);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) return true;
            if (input.equals("no")) return false;
            System.out.println("Please answer 'yes' or 'no'.");
        }
    }

    // Asking for a specific command from a list of valid commands. Keeps asking until it gets a valid one.
    public static String promptCommand(String prompt, String... validCommands) {
        System.out.println(prompt);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            for (String validCommand : validCommands) {
                if (input.equals(validCommand)) return input;
            }
            System.out.println("Invalid command. Please try again.");
        }
    }


    public static void closeScanner() {
        scanner.close();
    }

    // Asking for an integer. Keeps asking until it gets a valid one.
    public static int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print(prompt);
            }
        }
    }
}
