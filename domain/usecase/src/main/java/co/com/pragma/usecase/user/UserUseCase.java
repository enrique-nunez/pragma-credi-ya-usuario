package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class UserUseCase {
    private final UserRepository userRepository;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final BigDecimal SALARIO_MAXIMO = new BigDecimal("15000000");

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> registrarUsuario(User user) {
        user.setFechaCreacion(LocalDateTime.now());

        return validarDatos(user)
                .then(validarEmailUnico(user.getCorreoElectronico()))
                .then(userRepository.save(user));
    }

    public Mono<User> obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado")));
    }

    public Mono<User> obtenerUsuarioPorEmail(String correoElectronico) {
        return userRepository.findByCorreoElectronico(correoElectronico)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado")));
    }

    public Flux<User> obtenerTodosLosUsuarios() {
        return userRepository.findAll();
    }

    private Mono<Void> validarDatos(User user) {
        return Mono.fromRunnable(() -> {
            if (esNuloOVacio(user.getNombres())) {
                throw new IllegalArgumentException("Los nombres son obligatorios");
            }
            if (esNuloOVacio(user.getApellidos())) {
                throw new IllegalArgumentException("Los apellidos son obligatorios");
            }
            if (esNuloOVacio(user.getCorreoElectronico())) {
                throw new IllegalArgumentException("El correo electrónico es obligatorio");
            }
            if (user.getSalarioBase() == null) {
                throw new IllegalArgumentException("El salario base es obligatorio");
            }
            if (!EMAIL_PATTERN.matcher(user.getCorreoElectronico()).matches()) {
                throw new IllegalArgumentException("El formato del correo electrónico es inválido");
            }
            if (user.getSalarioBase().compareTo(BigDecimal.ZERO) < 0 ||
                    user.getSalarioBase().compareTo(SALARIO_MAXIMO) > 0) {
                throw new IllegalArgumentException("El salario base debe estar entre 0 y 15,000,000");
            }
        });
    }

    private Mono<Void> validarEmailUnico(String email) {
        return userRepository.findByCorreoElectronico(email)
                .hasElement()
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.error(new IllegalArgumentException(
                                "El correo electrónico ya está registrado"));
                    }
                    return Mono.empty();
                });
    }

    private boolean esNuloOVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
