package co.com.pragma.api;

import co.com.pragma.api.dto.UserCreateRequestDto;
import co.com.pragma.api.dto.UserResponseDto;
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

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    public Mono<ServerResponse> createUser(ServerRequest request){
        return request.bodyToMono(UserCreateRequestDto.class)
                .map(userDto -> userDto.withPassword(PasswordHelper.encryptPassword(userDto.password())))
                .map(userMapper::toUser)
                .flatMap(userUseCase::registerUser)
                .map(userMapper::toUserResponseDto)
                .flatMap(userDto -> {
                    BaseResponse<UserResponseDto> response = new BaseResponse<>(
                            true,
                            userDto,
                            ResponseMessages.USER_CREATED_SUCCESSFULLY
                    );
                    response.setStateCode(HttpStatusCode.CREATED.getValue());
                    return ServerResponse.status(HttpStatusCode.CREATED.getValue()).bodyValue(response);
                })
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return userUseCase.findUserById(id)
                .map(userMapper::toUserResponseDto)
                .flatMap(userDto -> {
                    BaseResponse<UserResponseDto> response = new BaseResponse<>(
                            true,
                            userDto,
                            ResponseMessages.USER_RETRIEVED_SUCCESSFULLY
                    );
                    response.setStateCode(HttpStatusCode.OK.getValue());
                    return ServerResponse.ok().bodyValue(response);
                })
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return userUseCase.findUserByEmail(email)
                .map(userMapper::toUserResponseDto)
                .flatMap(userDto -> {
                    BaseResponse<UserResponseDto> response = new BaseResponse<>(
                            true,
                            userDto,
                            ResponseMessages.USER_RETRIEVED_SUCCESSFULLY
                    );
                    response.setStateCode(HttpStatusCode.OK.getValue());
                    return ServerResponse.ok().bodyValue(response);
                })
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return userUseCase.findAllUsers()
                .map(userMapper::toUserResponseDto)
                .collectList()
                .flatMap(userDtos -> {
                    BaseResponse<Object> response = new BaseResponse<>(
                            true,
                            userDtos,
                            ResponseMessages.USERS_RETRIEVED_SUCCESSFULLY
                    );
                    response.setStateCode(HttpStatusCode.OK.getValue());
                    return ServerResponse.ok().bodyValue(response);
                })
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> checkUserExistsByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return userUseCase.existUserByEmail(email)
                .flatMap(exists -> {
                    BaseResponse<Boolean> response = new BaseResponse<>(
                            true,
                            exists,
                            exists ? ResponseMessages.USER_EXISTENCE_CHECKED_SUCCESSFULLY : ResponseMessages.USER_NOT_EXISTENCE
                    );
                    response.setStateCode(HttpStatusCode.OK.getValue());
                    return ServerResponse.ok().bodyValue(response);
                })
                .onErrorResume(Mono::error);
    }
}