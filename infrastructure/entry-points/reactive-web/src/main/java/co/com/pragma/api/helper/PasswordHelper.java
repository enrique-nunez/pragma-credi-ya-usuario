package co.com.pragma.api.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHelper {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean validatePassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}