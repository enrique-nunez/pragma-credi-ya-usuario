package co.com.pragma.model.role.exceptions;

import co.com.pragma.model.common.ErrorCode;

public class RoleNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public RoleNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}