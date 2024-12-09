package co.ke.spsat.bowip.payment.mpesa;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

public class IncrementingStringGenerator {
    private final SecureRandom random = new SecureRandom();
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int numericPart = 1;
    private String alphabeticPrefix = "Aa";

    public String generateIncrementingString() {
        // Generate random suffix
        String randomSuffix = generateRandomSuffix();

        // Build the final string with prefix, number, and suffix
        StringBuilder result = new StringBuilder();
        result.append(alphabeticPrefix);
        result.append(numericPart);
        result.append(randomSuffix);

        // Increment the numeric part
        numericPart++;

        // When numericPart exceeds a certain number, reset it and increment the alphabetic prefix
        if (numericPart > 9) {
            numericPart = 1;
            incrementAlphabeticPrefix();
        }

        return result.toString();
    }

    private void incrementAlphabeticPrefix() {
        char lastChar = alphabeticPrefix.charAt(1); // second character of prefix
        char firstChar = alphabeticPrefix.charAt(0); // first character of prefix

        // Check if we need to roll over the second character of the prefix
        if (lastChar == 'z') {
            lastChar = 'a'; // Reset to 'a'
            firstChar++; // Move the first character to the next letter
        } else {
            lastChar++; // Move the second character to the next letter
        }

        alphabeticPrefix = "" + firstChar + lastChar;
    }

    private String generateRandomSuffix() {
        int length = 8 + random.nextInt(3); // Random suffix of length between 8 and 10 characters
        StringBuilder suffix = new StringBuilder();

        for (int i = 0; i < length; i++) {
            suffix.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return suffix.toString();
    }
}
