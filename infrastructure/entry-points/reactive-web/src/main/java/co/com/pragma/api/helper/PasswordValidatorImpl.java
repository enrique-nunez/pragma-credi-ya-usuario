package co.com.pragma.api.helper;

import co.com.pragma.model.user.gateways.PasswordValidator;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidatorImpl implements PasswordValidator {

    @Override
    public boolean validate(String rawPassword, String encodedPassword) {
        return PasswordHelper.validatePassword(rawPassword, encodedPassword);
    }

}
