package co.com.pragma.model.common;

public enum ErrorCode {
    // Validation errors (400)
    FIRST_NAME_REQUIRED("APP_001", "First name is required"),
    LAST_NAME_REQUIRED("APP_002", "Last name is required"),
    EMAIL_REQUIRED("APP_003", "Email is required"),
    BASE_SALARY_REQUIRED("APP_004", "Base salary is required"),
    INVALID_EMAIL_FORMAT("APP_005", "Invalid email format"),
    INVALID_SALARY_RANGE("APP_006", "Base salary must be between 0 and 15,000,000"),
    VALIDATION_ERROR("APP_007", "Validation error"),
    //INVALID_CREDENTIALS("APP_008", "Credenciales inválidas"),

    // Role errors (404)
    ROLE_NOT_FOUND("ROL_NO_ENCONTRADO", "El rol especificado no existe"),

    // Conflict errors (409)
    EMAIL_ALREADY_EXISTS("APP_409", "Email is already registered"),

    // Not found errors (404)
    USER_NOT_FOUND("APP_404", "User not found"),
    ROUTE_NOT_FOUND("APP_404_ROUTE", "Route not found"),

    // Database errors (500)
    DATABASE_CONNECTION_ERROR("APP_500_DB", "Database connection error"),
    DATABASE_CONSTRAINT_VIOLATION("APP_500_CONSTRAINT", "Database constraint violation"),

    // Internal errors (500)
    INTERNAL_ERROR("APP_500", "Internal server error"),
    INVALID_INPUT("APP_INVALID_INPUT", "El input es inválido");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
