package co.com.pragma.api.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return route(POST("/api/users"), userHandler::createUser)
                .andRoute(GET("/api/users/{id}"), userHandler::getUserById)
                .andRoute(GET("/api/users/email/{email}"), userHandler::getUserByEmail)
                .andRoute(GET("/api/users"), userHandler::getAllUsers);
    }
}