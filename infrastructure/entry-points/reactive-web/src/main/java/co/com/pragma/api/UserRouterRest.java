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
@Tag(name = "UserHandler", description = "Operaciones para la administración de usuarios")
public class UserRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/usuarios", method = RequestMethod.POST,
                    operation = @Operation(operationId = "createUser", summary = "Crear usuario",
                            description = "Crea un nuevo usuario en el sistema",
                            tags = {"UserHandler"},
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
                            })),
            @RouterOperation(path = "/api/v1/usuarios/{id}", method = RequestMethod.GET,
                    operation = @Operation(operationId = "getUserById", summary = "Obtener usuario por ID",
                            tags = {"UserHandler"},
                            parameters = @Parameter(name = "id", in = ParameterIn.PATH, description = "ID del usuario"),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
                            })),
            @RouterOperation(path = "/api/v1/usuarios/email/{email}", method = RequestMethod.GET,
                    operation = @Operation(operationId = "getUserByEmail", summary = "Obtener usuario por email",
                            tags = {"UserHandler"},
                            parameters = @Parameter(name = "email", in = ParameterIn.PATH, description = "Email del usuario"))),
            @RouterOperation(path = "/api/v1/usuarios", method = RequestMethod.GET,
                    operation = @Operation(operationId = "getAllUsers", summary = "Listar todos los usuarios",
                            tags = {"UserHandler"}))
    })
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return route(POST("/api/v1/usuarios"), userHandler::createUser)
                .andRoute(GET("/api/v1/usuarios/{id}"), userHandler::getUserById)
                .andRoute(GET("/api/v1/usuarios/email/{email}"), userHandler::getUserByEmail)
                .andRoute(GET("/api/v1/usuarios"), userHandler::getAllUsers);
    }
}