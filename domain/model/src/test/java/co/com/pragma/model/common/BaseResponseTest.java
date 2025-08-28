package co.com.pragma.model.common;


import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BaseResponseTest {

    @Test
    void defaultConstructor_ShouldCreateEmptyResponse() {
        BaseResponse<String> response = new BaseResponse<>();

        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertNull(response.getException());
        assertNull(response.getMessage());
        assertNull(response.getStateCode());
        assertNull(response.getPagination());
    }

    @Test
    void constructorWithSuccessMessageAndStateCode_ShouldSetFields() {
        // Usa el constructor (boolean, String, Object) y luego verifica
        BaseResponse<String> response = new BaseResponse<>(true, "Success message", (Object) "200");

        assertTrue(response.isSuccess());
        assertEquals("Success message", response.getMessage());
        assertEquals("200", response.getStateCode());
        assertNull(response.getData());
        assertNull(response.getException());
        assertNull(response.getPagination());
    }

    @Test
    void constructorWithSuccessDataAndMessage_ShouldSetFields() {
        // Usa el constructor (boolean, T, String)
        String testData = "Test data";
        BaseResponse<String> response = new BaseResponse<String>(true, testData, "Operation successful");

        assertTrue(response.isSuccess());
        assertEquals(testData, response.getData());
        assertEquals("Operation successful", response.getMessage());
        assertNull(response.getException());
        assertNull(response.getStateCode());
        assertNull(response.getPagination());
    }

    @Test
    void constructorWithStateCodeAsInteger_ShouldSetFields() {
        // Test del constructor (boolean, String, Object) con Object no String
        BaseResponse<String> response = new BaseResponse<>(true, "Success message", 200);

        assertTrue(response.isSuccess());
        assertEquals("Success message", response.getMessage());
        assertEquals(200, response.getStateCode());
        assertNull(response.getData());
    }

    @Test
    void setSuccess_ShouldUpdateSuccessField() {
        BaseResponse<String> response = new BaseResponse<>();

        response.setSuccess(true);
        assertTrue(response.isSuccess());

        response.setSuccess(false);
        assertFalse(response.isSuccess());
    }

    @Test
    void setData_ShouldUpdateDataField() {
        BaseResponse<String> response = new BaseResponse<>();
        String testData = "Test data";

        response.setData(testData);
        assertEquals(testData, response.getData());
    }

    @Test
    void setException_ShouldUpdateExceptionField() {
        BaseResponse<String> response = new BaseResponse<>();
        Exception testException = new RuntimeException("Test exception");

        response.setException(testException);
        assertEquals(testException, response.getException());
    }

    @Test
    void setMessage_ShouldUpdateMessageField() {
        BaseResponse<String> response = new BaseResponse<>();
        String testMessage = "Test message";

        response.setMessage(testMessage);
        assertEquals(testMessage, response.getMessage());
    }

    @Test
    void setStateCode_ShouldUpdateStateCodeField() {
        BaseResponse<String> response = new BaseResponse<>();
        String testStateCode = "404";

        response.setStateCode(testStateCode);
        assertEquals(testStateCode, response.getStateCode());
    }

    @Test
    void setPagination_ShouldUpdatePaginationField() {
        BaseResponse<String> response = new BaseResponse<>();
        Object testPagination = new Object();

        response.setPagination(testPagination);
        assertEquals(testPagination, response.getPagination());
    }

    @Test
    void baseResponse_WithGenericType_ShouldWorkCorrectly() {
        // Constructor (boolean, T, String) con Integer como tipo genérico
        BaseResponse<Integer> response = new BaseResponse<>(true, 42, "Number response");

        assertTrue(response.isSuccess());
        assertEquals(Integer.valueOf(42), response.getData());
        assertEquals("Number response", response.getMessage());
    }

    @Test
    void baseResponse_WithComplexObject_ShouldWorkCorrectly() {
        Object complexObject = new Object();
        BaseResponse<Object> response = new BaseResponse<>(false, complexObject, "Complex object response");

        assertFalse(response.isSuccess());
        assertEquals(complexObject, response.getData());
        assertEquals("Complex object response", response.getMessage());
    }
}