package pay.point.sample;

import java.util.*;

public class SecretWordFinder {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MAX_LENGTH = 12;
    private static String secretWord;

    public static void main(String[] args) {
        // Set the secret word
        secretWord = "narrowzoo408"; // Replace with the actual secret word
        
        // Start the search
        generateCombinations(new StringBuilder(), 0);
    }

    private static void generateCombinations(StringBuilder prefix, int length) {
        if (length > MAX_LENGTH) {
            return;
        }

        // Check if the current combination is the secret word
        if (prefix.toString().equals(secretWord)) {
            System.out.println("Secret word found: " + prefix);
            System.exit(0);
        }

        // Recursively generate all possible combinations
        for (int i = 0; i < CHARACTERS.length(); i++) {
            prefix.append(CHARACTERS.charAt(i));
            generateCombinations(prefix, length + 1);
            prefix.deleteCharAt(prefix.length() - 1); // Backtrack
            System.out.println(prefix);
        }
    }
}
