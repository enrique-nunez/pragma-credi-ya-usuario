package co.com.pragma.model.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusCodeTest {

    @Test
    void getValue_ShouldReturnCorrectValueForOK() {
        assertEquals(200, HttpStatusCode.OK.getValue());
    }

    @Test
    void getValue_ShouldReturnCorrectValueForCreated() {
        assertEquals(201, HttpStatusCode.CREATED.getValue());
    }

    @Test
    void getValue_ShouldReturnCorrectValueForBadRequest() {
        assertEquals(400, HttpStatusCode.BAD_REQUEST.getValue());
    }

    @Test
    void getValue_ShouldReturnCorrectValueForNotFound() {
        assertEquals(404, HttpStatusCode.NOT_FOUND.getValue());
    }

    @Test
    void getValue_ShouldReturnCorrectValueForUnauthorized() {
        assertEquals(401, HttpStatusCode.UNAUTHORIZED.getValue());
    }

    @Test
    void getValue_ShouldReturnCorrectValueForForbidden() {
        assertEquals(403, HttpStatusCode.FORBIDDEN.getValue());
    }

    @Test
    void getValue_ShouldReturnCorrectValueForInternalServerError() {
        assertEquals(500, HttpStatusCode.INTERNAL_SERVER_ERROR.getValue());
    }

    @Test
    void values_ShouldReturnAllEnumConstants() {
        HttpStatusCode[] values = HttpStatusCode.values();

        assertEquals(7, values.length);
        assertTrue(containsValue(values, HttpStatusCode.OK));
        assertTrue(containsValue(values, HttpStatusCode.CREATED));
        assertTrue(containsValue(values, HttpStatusCode.BAD_REQUEST));
        assertTrue(containsValue(values, HttpStatusCode.NOT_FOUND));
        assertTrue(containsValue(values, HttpStatusCode.UNAUTHORIZED));
        assertTrue(containsValue(values, HttpStatusCode.FORBIDDEN));
        assertTrue(containsValue(values, HttpStatusCode.INTERNAL_SERVER_ERROR));
    }

    @Test
    void valueOf_ShouldReturnCorrectEnumConstant() {
        assertEquals(HttpStatusCode.OK, HttpStatusCode.valueOf("OK"));
        assertEquals(HttpStatusCode.CREATED, HttpStatusCode.valueOf("CREATED"));
        assertEquals(HttpStatusCode.BAD_REQUEST, HttpStatusCode.valueOf("BAD_REQUEST"));
        assertEquals(HttpStatusCode.NOT_FOUND, HttpStatusCode.valueOf("NOT_FOUND"));
        assertEquals(HttpStatusCode.UNAUTHORIZED, HttpStatusCode.valueOf("UNAUTHORIZED"));
        assertEquals(HttpStatusCode.FORBIDDEN, HttpStatusCode.valueOf("FORBIDDEN"));
        assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, HttpStatusCode.valueOf("INTERNAL_SERVER_ERROR"));
    }

    @Test
    void valueOf_WithInvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            HttpStatusCode.valueOf("INVALID_STATUS");
        });
    }

    @Test
    void enumConstants_ShouldHaveCorrectNames() {
        assertEquals("OK", HttpStatusCode.OK.name());
        assertEquals("CREATED", HttpStatusCode.CREATED.name());
        assertEquals("BAD_REQUEST", HttpStatusCode.BAD_REQUEST.name());
        assertEquals("NOT_FOUND", HttpStatusCode.NOT_FOUND.name());
        assertEquals("UNAUTHORIZED", HttpStatusCode.UNAUTHORIZED.name());
        assertEquals("FORBIDDEN", HttpStatusCode.FORBIDDEN.name());
        assertEquals("INTERNAL_SERVER_ERROR", HttpStatusCode.INTERNAL_SERVER_ERROR.name());
    }

    private boolean containsValue(HttpStatusCode[] values, HttpStatusCode target) {
        for (HttpStatusCode value : values) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }
}