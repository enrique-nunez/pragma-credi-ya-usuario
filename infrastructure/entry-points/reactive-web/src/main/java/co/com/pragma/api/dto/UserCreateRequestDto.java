package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserCreateRequestDto(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "Phone is required")
        String phone,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Base salary is required")
        @DecimalMin(value = "0.01", message = "Base salary must be greater than 0")
        @DecimalMax(value = "15000000", message = "Base salary must not exceed 15,000,000")
        BigDecimal baseSalary,

        @NotNull(message = "Role ID is required")
        @Positive(message = "Role ID must be a positive number")
        Long roleId
) {}
