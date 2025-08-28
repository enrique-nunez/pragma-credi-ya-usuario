package co.com.pragma.model.user;

import co.com.pragma.model.common.ErrorCode;
import co.com.pragma.model.user.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidInputExceptionTest {

    @Test
    void constructor_ShouldSetErrorCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        InvalidInputException exception = new InvalidInputException(errorCode);

        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void getErrorCode_ShouldReturnCorrectErrorCode() {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        InvalidInputException exception = new InvalidInputException(errorCode);

        ErrorCode result = exception.getErrorCode();

        assertEquals(errorCode, result);
    }

    @Test
    void exception_ShouldExtendRuntimeException() {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        InvalidInputException exception = new InvalidInputException(errorCode);

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithDifferentErrorCode_ShouldWork() {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        InvalidInputException exception = new InvalidInputException(errorCode);

        assertNotNull(exception.getErrorCode());
        assertEquals(errorCode, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }
}