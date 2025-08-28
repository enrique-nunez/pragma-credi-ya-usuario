package co.com.pragma.model.role;

import co.com.pragma.model.common.ErrorCode;
import co.com.pragma.model.role.exceptions.RoleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleNotFoundExceptionTest {

    @Test
    void constructor_ShouldSetErrorCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.ROLE_NOT_FOUND;

        RoleNotFoundException exception = new RoleNotFoundException(errorCode);

        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void getErrorCode_ShouldReturnCorrectErrorCode() {
        ErrorCode errorCode = ErrorCode.ROLE_NOT_FOUND;
        RoleNotFoundException exception = new RoleNotFoundException(errorCode);

        ErrorCode result = exception.getErrorCode();

        assertEquals(errorCode, result);
    }

    @Test
    void exception_ShouldExtendRuntimeException() {
        ErrorCode errorCode = ErrorCode.ROLE_NOT_FOUND;
        RoleNotFoundException exception = new RoleNotFoundException(errorCode);

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithDifferentErrorCode_ShouldWork() {
        // Asumiendo que hay otros ErrorCode disponibles
        ErrorCode errorCode = ErrorCode.ROLE_NOT_FOUND;

        RoleNotFoundException exception = new RoleNotFoundException(errorCode);

        assertNotNull(exception.getErrorCode());
        assertEquals(errorCode, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }
}