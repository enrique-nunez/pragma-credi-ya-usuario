package co.com.pragma.model.user.gateways;

public interface PasswordValidator {
    boolean validate(String rawPassword, String encodedPassword);
}
