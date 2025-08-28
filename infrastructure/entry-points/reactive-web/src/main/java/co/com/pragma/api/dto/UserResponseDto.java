package co.com.pragma.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String address,
        String phone,
        String email,
        BigDecimal baseSalary,
        LocalDateTime creationDate,
        RoleDto role
) {}