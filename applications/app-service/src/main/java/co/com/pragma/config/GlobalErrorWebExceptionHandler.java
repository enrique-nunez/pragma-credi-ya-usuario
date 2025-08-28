package co.com.pragma.config;

import co.com.pragma.model.common.BaseResponse;
import co.com.pragma.model.common.ErrorDetails;
import co.com.pragma.model.common.HttpStatusCode;
import co.com.pragma.model.user.exceptions.InvalidInputException;
import co.com.pragma.model.user.exceptions.UserNotFoundException;
import co.com.pragma.model.common.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalErrorWebExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
        ErrorDetails errorDetails = buildErrorResponse(ex);

        BaseResponse<Object> response = new BaseResponse<>(
                false,
                errorDetails.getMessage(),
                errorDetails.getStatusCode()
        );
        response.setException(errorDetails.getCode());

        // Mapear de HttpStatusCode a HttpStatus de Spring
        HttpStatus springStatus = HttpStatus.valueOf(errorDetails.getStatusCode());
        exchange.getResponse().setStatusCode(springStatus);
        exchange.getResponse().getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        try {
            String responseJson = objectMapper.writeValueAsString(response);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(responseJson.getBytes());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return exchange.getResponse().setComplete();
        }
    }

    private ErrorDetails buildErrorResponse(Throwable ex) {
        if (ex instanceof org.springframework.web.server.ResponseStatusException) {
            org.springframework.web.server.ResponseStatusException rse = (org.springframework.web.server.ResponseStatusException) ex;

            if (rse.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new ErrorDetails(
                        HttpStatusCode.NOT_FOUND,
                        ErrorCode.ROUTE_NOT_FOUND.getCode(),
                        ErrorCode.ROUTE_NOT_FOUND.getMessage()
                );
            } else {
                return new ErrorDetails(
                        HttpStatusCode.valueOf(String.valueOf(rse.getStatusCode().value())),
                        "HTTP_" + rse.getStatusCode().value(),
                        rse.getReason() != null ? rse.getReason() : "Error HTTP"
                );
            }
        } else if (ex instanceof InvalidInputException) {
            InvalidInputException inputEx = (InvalidInputException) ex;
            return new ErrorDetails(
                    HttpStatusCode.BAD_REQUEST,
                    inputEx.getErrorCode().getCode(),
                    inputEx.getErrorCode().getMessage()
            );
        } else if (ex instanceof UserNotFoundException) {
            UserNotFoundException notFoundEx = (UserNotFoundException) ex;
            return new ErrorDetails(
                    HttpStatusCode.NOT_FOUND,
                    notFoundEx.getErrorCode().getCode(),
                    notFoundEx.getErrorCode().getMessage()
            );
        } else if (ex instanceof IllegalArgumentException) {
            return new ErrorDetails(
                    HttpStatusCode.BAD_REQUEST,
                    ErrorCode.VALIDATION_ERROR.getCode(),
                    ex.getMessage()
            );
        } else {
            return new ErrorDetails(
                    HttpStatusCode.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR.getCode(),
                    ErrorCode.INTERNAL_ERROR.getMessage()
            );
        }
    }
}