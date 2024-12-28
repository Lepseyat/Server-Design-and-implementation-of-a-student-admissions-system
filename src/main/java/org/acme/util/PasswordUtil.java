package org.acme.util;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_PASSWORD_LENGTH = 12;
    private static final Random RANDOM = new SecureRandom();

    // Generate a secure random password with a specified length
    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    // Overloaded method: Generate a random password with the default length
    public static String generateRandomPassword() {
        return generateRandomPassword(DEFAULT_PASSWORD_LENGTH);
    }

    // Encode a password securely using BCrypt
    public static String encodePassword(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    // Verify a password against the encoded hash
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
