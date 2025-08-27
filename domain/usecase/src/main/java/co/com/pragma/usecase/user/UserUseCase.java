package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.InvalidInputException;
import co.com.pragma.model.common.ErrorCode;
import co.com.pragma.model.user.exceptions.UserNotFoundException;
import co.com.pragma.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import java.util.logging.Logger;
import java.util.logging.Level;

public class UserUseCase {

    private static final Logger logger = Logger.getLogger(UserUseCase.class.getName());

    private final UserRepository userRepository;

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> registerUser(User user) {
        logger.info("Iniciando registro de usuario con email: {}" + user.getEmail());
        user.setCreationDate(LocalDateTime.now());

        return validateUniqueEmail(user.getEmail())
                .then(userRepository.save(user))
                .doOnSuccess(savedUser ->
                        logger.info("Usuario registrado exitosamente con ID: {}" + savedUser.getId()))
                .doOnError(error ->
                        logger.log(Level.SEVERE, "Error al registrar usuario con email " + user.getEmail() + ": " + error.getMessage()));
    }

    public Mono<User> findUserById(Long id) {
        logger.info("Buscando usuario por ID: " + id);
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(ErrorCode.USER_NOT_FOUND)));
    }

    public Mono<User> findUserByEmail(String email) {
        logger.info("Buscando usuario por email: " + email);
        return userRepository.findByEmail(email)
                .doOnSuccess(user -> {
                    if (user != null) {
                        logger.info("Usuario encontrado:: {}" + user.getFirstName());
                    }
                })
                .doOnError(error -> logger.log(Level.SEVERE, "Error al buscar usuario con email " + email + ": " + error.getMessage()))
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warning("Usuario no encontrado con email: {}" + email);
                    return Mono.error(new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
                }));
    }

    public Flux<User> findAllUsers() {
        logger.info("Obteniendo todos los usuarios");
        return userRepository.findAll()
                .doOnComplete(() -> logger.info("Todos los usuarios obtenidos exitosamente"))
                .doOnError(error -> logger.log(Level.SEVERE, "Error al obtener usuarios: " + error.getMessage()));
    }

    private Mono<Void> validateUniqueEmail(String email) {
        logger.info("Validando unicidad del email: {}" + email);
        return userRepository.findByEmail(email)
                .hasElement()
                .doOnNext(exists -> logger.info("Resultado validación email único " + email + ": " + !exists))
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.error(new InvalidInputException(ErrorCode.EMAIL_ALREADY_EXISTS));
                    }
                    return Mono.empty();
                });
    }
}
