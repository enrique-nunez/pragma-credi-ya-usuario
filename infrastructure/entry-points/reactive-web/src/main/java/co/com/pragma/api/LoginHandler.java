package co.com.pragma.api;

import co.com.pragma.api.dto.UserCreateRequestDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.api.helper.JwtService;
import co.com.pragma.api.helper.PasswordHelper;
import co.com.pragma.api.mapper.UserMapper;
import co.com.pragma.model.common.BaseResponse;
import co.com.pragma.model.common.HttpStatusCode;
import co.com.pragma.model.common.ResponseMessages;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final UserUseCase userUseCase;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public Mono<ServerResponse> login(ServerRequest request){
        return request.bodyToMono(UserCreateRequestDto.class)
                .flatMap(userDto -> userUseCase.authenticateUser(userDto.email(), userDto.password()))
                .map(userMapper::toUserResponseDto)
                .flatMap(userDto -> {
                    // Genera el token JWT
                    String token = jwtService.generate(
                            userDto.id(),
                            userDto.email(),
                            (userDto.role().name())
                    );

                    // Solo expone token y tiempo de expiración
                    Map<String, Object> loginData = Map.of(
                            "token", token,
                            "expireIn", 3600 // 1 hora en segundos, ajusta según tu configuración
                    );

                    BaseResponse<Map<String, Object>> response = new BaseResponse<>(
                            true,
                            loginData,
                            "Login exitoso"
                    );
                    response.setStateCode(HttpStatusCode.OK.getValue());
                    return ServerResponse.ok().bodyValue(response);
                })
                .onErrorResume(throwable -> {
                    BaseResponse<Map<String, Object>> errorResponse = new BaseResponse<>(
                            false,
                            Map.of(),
                            "Credenciales inválidas"
                    );
                    errorResponse.setStateCode(HttpStatusCode.UNAUTHORIZED.getValue());
                    return ServerResponse.status(401).bodyValue(errorResponse);
                });
    }

}