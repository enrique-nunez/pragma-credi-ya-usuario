package co.com.pragma.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = "LoginHandler", description = "Operaciones para la autenticación de usuarios")
public class LoginRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/login",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "login",
                            summary = "Iniciar sesión de usuario",
                            description = "Permite a un usuario iniciar sesión proporcionando sus credenciales.",
                            tags = {"LoginHandler"},
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                    name = "EjemploLogin",
                                                    value = "{ \"email\": \"prueba@gmail.com\", \"lastName\": \"Pérez\" }"
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Usuario autenticado exitosamente",
                                            content = @io.swagger.v3.oas.annotations.media.Content(
                                                    mediaType = "application/json",
                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                            name = "UsuarioRespuesta",
                                                            value = "{ \"id\": 1, \"firstName\": \"Juan\", \"lastName\": \"Pérez\", \"birthDate\": \"1990-05-15\", \"address\": \"Calle 123 #45-67\", \"phone\": \"+573001234567\", \"email\": \"juan.perez@correo.com\", \"baseSalary\": 2500000.00, \"creationDate\": \"2024-01-15T10:30:45\", \"role\": { \"id\": 1, \"name\": \"Empleado\", \"description\": \"Empleado regular\" } }"
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> loginRouterFunction(LoginHandler loginHandler) {
        return route(POST("/api/v1/login"), loginHandler::login);
    }
}