package co.com.pragma.model.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDetailsTest {

    @Test
    void constructor_ShouldSetAllFields() {
        HttpStatusCode status = HttpStatusCode.BAD_REQUEST;
        String code = "USER_001";
        String message = "Usuario no encontrado";

        ErrorDetails errorDetails = new ErrorDetails(status, code, message);

        assertEquals(status, errorDetails.getStatus());
        assertEquals(code, errorDetails.getCode());
        assertEquals(message, errorDetails.getMessage());
        assertEquals(400, errorDetails.getStatusCode());
    }

    @Test
    void getCode_ShouldReturnCorrectCode() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatusCode.NOT_FOUND, "ERR_404", "Recurso no encontrado");

        assertEquals("ERR_404", errorDetails.getCode());
    }

    @Test
    void getMessage_ShouldReturnCorrectMessage() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatusCode.INTERNAL_SERVER_ERROR, "ERR_500", "Error interno del servidor");

        assertEquals("Error interno del servidor", errorDetails.getMessage());
    }

    @Test
    void getStatusCode_ShouldReturnCorrectStatusCode() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatusCode.UNAUTHORIZED, "ERR_401", "No autorizado");

        assertEquals(401, errorDetails.getStatusCode());
    }

    @Test
    void getStatus_ShouldReturnCorrectHttpStatusCode() {
        HttpStatusCode expectedStatus = HttpStatusCode.FORBIDDEN;
        ErrorDetails errorDetails = new ErrorDetails(expectedStatus, "ERR_403", "Acceso prohibido");

        assertEquals(expectedStatus, errorDetails.getStatus());
    }

    @Test
    void errorDetails_WithDifferentStatusCodes_ShouldWorkCorrectly() {
        ErrorDetails badRequest = new ErrorDetails(HttpStatusCode.BAD_REQUEST, "BAD_REQ", "Solicitud incorrecta");
        ErrorDetails notFound = new ErrorDetails(HttpStatusCode.NOT_FOUND, "NOT_FOUND", "No encontrado");

        assertEquals(400, badRequest.getStatusCode());
        assertEquals(404, notFound.getStatusCode());
        assertEquals("BAD_REQ", badRequest.getCode());
        assertEquals("NOT_FOUND", notFound.getCode());
    }

    @Test
    void errorDetails_WithNullValues_ShouldHandleCorrectly() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatusCode.BAD_REQUEST, null, null);

        assertNull(errorDetails.getCode());
        assertNull(errorDetails.getMessage());
        assertEquals(HttpStatusCode.BAD_REQUEST, errorDetails.getStatus());
        assertEquals(400, errorDetails.getStatusCode());
    }
}