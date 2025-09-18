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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = "UserHandler", description = "Operaciones para la administración de usuarios")
public class UserRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Crear usuario",
                            description = "Crea un nuevo usuario en el sistema",
                            tags = {"UserHandler"},
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                    name = "EjemploUsuario",
                                                    value = "{ \"firstName\": \"Juan\", \"lastName\": \"Pérez\", \"birthDate\": \"1990-05-15\", \"address\": \"Calle 123 #45-67\", \"phone\": \"+573001234567\", \"email\": \"juan.perez@correo.com\", \"baseSalary\": 2500000.00, \"roleId\": 1 }"
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
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
            ),
//            @RouterOperation(
//                    path = "/api/v1/usuarios/{id}",
//                    method = RequestMethod.GET,
//                    operation = @Operation(
//                            operationId = "getUserById",
//                            summary = "Obtener usuario por ID",
//                            parameters = @Parameter(name = "id", in = ParameterIn.PATH, description = "ID del usuario"),
//                            tags = {"UserHandler"},
//                            responses = {
//                                    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
//                                            content = @io.swagger.v3.oas.annotations.media.Content(
//                                                    mediaType = "application/json",
//                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
//                                                            name = "UsuarioRespuesta",
//                                                            value = "{ \"id\": 1, \"firstName\": \"Juan\", \"lastName\": \"Pérez\", \"birthDate\": \"1990-05-15\", \"address\": \"Calle 123 #45-67\", \"phone\": \"+573001234567\", \"email\": \"juan.perez@correo.com\", \"baseSalary\": 2500000.00, \"creationDate\": \"2024-01-15T10:30:45\", \"role\": { \"id\": 1, \"name\": \"Empleado\", \"description\": \"Empleado regular\" } }"
//                                                    )
//                                            )
//                                    ),
//                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
//                            }
//                    )
//            ),
//            @RouterOperation(
//                    path = "/api/v1/usuarios/email/{email}",
//                    method = RequestMethod.GET,
//                    operation = @Operation(
//                            operationId = "getUserByEmail",
//                            summary = "Obtener usuario por email",
//                            parameters = @Parameter(name = "email", in = ParameterIn.PATH, description = "Email del usuario"),
//                            tags = {"UserHandler"},
//                            responses = {
//                                    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
//                                            content = @io.swagger.v3.oas.annotations.media.Content(
//                                                    mediaType = "application/json",
//                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
//                                                            name = "UsuarioRespuesta",
//                                                            value = "{ \"id\": 1, \"firstName\": \"Juan\", \"lastName\": \"Pérez\", \"birthDate\": \"1990-05-15\", \"address\": \"Calle 123 #45-67\", \"phone\": \"+573001234567\", \"email\": \"juan.perez@correo.com\", \"baseSalary\": 2500000.00, \"creationDate\": \"2024-01-15T10:30:45\", \"role\": { \"id\": 1, \"name\": \"Empleado\", \"description\": \"Empleado regular\" } }"
//                                                    )
//                                            )
//                                    ),
//                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
//                            }
//                    )
//            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAllUsers",
                            summary = "Listar todos los usuarios",
                            tags = {"UserHandler"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                                            content = @io.swagger.v3.oas.annotations.media.Content(
                                                    mediaType = "application/json",
                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                            name = "ListaUsuarios",
                                                            value = "[{ \"id\": 1, \"firstName\": \"Juan\", \"lastName\": \"Pérez\", \"birthDate\": \"1990-05-15\", \"address\": \"Calle 123 #45-67\", \"phone\": \"+573001234567\", \"email\": \"juan.perez@correo.com\", \"baseSalary\": 2500000.00, \"creationDate\": \"2024-01-15T10:30:45\", \"role\": { \"id\": 1, \"name\": \"Empleado\", \"description\": \"Empleado regular\" } }]"
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/validar/{email}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "checkUserExistsByEmail",
                            summary = "Validar existencia de usuario por email",
                            description = "Verifica si existe un usuario con el email proporcionado",
                            tags = {"UserHandler"},
                            parameters = @Parameter(
                                    name = "email",
                                    in = ParameterIn.PATH,
                                    description = "Email del usuario a validar"
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Resultado de la validación",
                                            content = @io.swagger.v3.oas.annotations.media.Content(
                                                    mediaType = "application/json",
                                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                            name = "ValidaciónUsuario",
                                                            value = "{ \"data\": true, \"message\": \"El usuario existe\", \"success\": true }"
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Usuario no encontrado"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return route(PUT("/api/v1/usuarios"), userHandler::createUser)
//                .andRoute(GET("/api/v1/usuarios/{id}"), userHandler::getUserById)
//                .andRoute(GET("/api/v1/usuarios/email/{email}"), userHandler::getUserByEmail)
                .andRoute(GET("/api/v1/usuarios"), userHandler::getAllUsers)
                .andRoute(GET("/api/v1/usuarios/validar/{email}"), userHandler::checkUserExistsByEmail);
    }
}