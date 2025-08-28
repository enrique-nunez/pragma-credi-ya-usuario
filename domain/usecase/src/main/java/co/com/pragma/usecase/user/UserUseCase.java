package co.com.pragma.usecase.user;

import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.InvalidInputException;
import co.com.pragma.model.common.ErrorCode;
import co.com.pragma.model.user.exceptions.UserNotFoundException;
import co.com.pragma.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class UserUseCase {

    private static final Logger logger = Logger.getLogger(UserUseCase.class.getName());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final BigDecimal MAX_SALARY = new BigDecimal("15000000");

    public UserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Mono<User> registerUser(User user) {
        logger.info("Iniciando registro de usuario con email: {}" + user.getEmail());
        user.setCreationDate(LocalDateTime.now());

        return validateUserData(user)
                .then(validateUniqueEmail(user.getEmail()))
                .then(userRepository.save(user))
                .doOnSuccess(savedUser ->
                        logger.info("Usuario registrado exitosamente con ID: {}" + savedUser.getId()))
                .doOnError(error ->
                        logger.log(Level.SEVERE, "Error al registrar usuario con email " + user.getEmail() + ": " + error.getMessage()));
    }

    public Mono<User> findUserById(Long id) {
        logger.info("Buscando usuario por ID: " + id);
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(ErrorCode.USER_NOT_FOUND)))
                .flatMap(user ->
                        roleRepository.findById(user.getRoleId())
                                .map(role -> {
                                    user.setRole(role);
                                    return user;
                                })
                );
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
                .flatMap(user ->
                        roleRepository.findById(user.getRoleId())
                                .map(role -> {
                                    user.setRole(role);
                                    return user;
                                })
                                .defaultIfEmpty(user)
                )
                .doOnComplete(() -> logger.info("Todos los usuarios con roles obtenidos exitosamente"))
                .doOnError(error -> logger.log(Level.SEVERE, "Error al obtener usuarios con roles: " + error.getMessage()));
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

    private Mono<Void> validateUserData(User user) {
        if (isNullOrEmpty(user.getFirstName())) {
            return Mono.error(new InvalidInputException(ErrorCode.FIRST_NAME_REQUIRED));
        }
        if (isNullOrEmpty(user.getLastName())) {
            return Mono.error(new InvalidInputException(ErrorCode.LAST_NAME_REQUIRED));
        }
        if (isNullOrEmpty(user.getEmail())) {
            return Mono.error(new InvalidInputException(ErrorCode.EMAIL_REQUIRED));
        }
        if (user.getBaseSalary() == null) {
            return Mono.error(new InvalidInputException(ErrorCode.BASE_SALARY_REQUIRED));
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return Mono.error(new InvalidInputException(ErrorCode.INVALID_EMAIL_FORMAT));
        }
        if (user.getBaseSalary().compareTo(BigDecimal.ZERO) < 0 ||
                user.getBaseSalary().compareTo(MAX_SALARY) > 0) {
            return Mono.error(new InvalidInputException(ErrorCode.INVALID_SALARY_RANGE));
        }
        return Mono.empty();
    }

    private boolean isNullOrEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
