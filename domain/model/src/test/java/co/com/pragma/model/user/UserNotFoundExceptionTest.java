package co.com.pragma.model.user;

import co.com.pragma.model.common.ErrorCode;
import co.com.pragma.model.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void constructor_ShouldSetErrorCodeAndMessage() {
        ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

        UserNotFoundException exception = new UserNotFoundException(errorCode);

        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void getErrorCode_ShouldReturnCorrectErrorCode() {
        ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;
        UserNotFoundException exception = new UserNotFoundException(errorCode);

        ErrorCode result = exception.getErrorCode();

        assertEquals(errorCode, result);
    }

    @Test
    void exception_ShouldExtendRuntimeException() {
        ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;
        UserNotFoundException exception = new UserNotFoundException(errorCode);

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithDifferentErrorCode_ShouldWork() {
        ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

        UserNotFoundException exception = new UserNotFoundException(errorCode);

        assertNotNull(exception.getErrorCode());
        assertEquals(errorCode, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }
}