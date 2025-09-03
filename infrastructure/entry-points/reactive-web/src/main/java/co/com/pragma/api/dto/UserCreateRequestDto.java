package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserCreateRequestDto(
        String firstName,
        String lastName,
        LocalDate birthDate,
        String address,
        String phone,
        String email,
        BigDecimal baseSalary,
        Long roleId,
        String password
) {

        public UserCreateRequestDto withPassword(String newPassword) {
                return new UserCreateRequestDto(
                        this.firstName(),
                        this.lastName(),
                        this.birthDate(),
                        this.address(),
                        this.phone(),
                        this.email(),
                        this.baseSalary(),
                        this.roleId(),
                        newPassword
                );
        }
}
