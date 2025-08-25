package co.com.pragma.api.user;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(userUseCase::registrarUsuario)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(IllegalArgumentException.class,
                        error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return userUseCase.obtenerUsuarioPorId(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(IllegalArgumentException.class,
                        error -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return userUseCase.obtenerUsuarioPorEmail(email)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(IllegalArgumentException.class,
                        error -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .body(userUseCase.obtenerTodosLosUsuarios(), User.class);
    }
}