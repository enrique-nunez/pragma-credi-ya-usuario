package co.com.pragma.model.common;

public class BaseResponse<T> {
    private boolean success;
    private T data;
    private Object exception;
    private String message;
    private Object stateCode;
    private Object pagination;

    public BaseResponse() {}

    public BaseResponse(boolean success, String message, Object stateCode) {
        this.success = success;
        this.message = message;
        this.stateCode = stateCode;
    }

    public BaseResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public Object getException() { return exception; }
    public void setException(Object exception) { this.exception = exception; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getStateCode() { return stateCode; }
    public void setStateCode(Object stateCode) { this.stateCode = stateCode; }

    public Object getPagination() { return pagination; }
    public void setPagination(Object pagination) { this.pagination = pagination; }
}
