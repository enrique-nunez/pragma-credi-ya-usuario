package co.com.pragma.model.common;

public class ErrorDetails {
    private final HttpStatusCode status;
    private final String code;
    private final String message;

    public ErrorDetails(HttpStatusCode status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return status.getValue();
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
