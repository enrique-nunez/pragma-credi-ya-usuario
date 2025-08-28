package co.com.pragma.usecase.role;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.exceptions.RoleNotFoundException;
import co.com.pragma.model.common.ErrorCode;
import co.com.pragma.model.role.gateways.RoleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;
import java.util.logging.Level;

public class RoleUseCase {

    private static final Logger logger = Logger.getLogger(RoleUseCase.class.getName());

    private final RoleRepository roleRepository;

    public RoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<Role> findRoleById(Long id) {
        logger.info("Buscando rol por ID: " + id);
        return roleRepository.findById(id)
                .doOnSuccess(role -> {
                    if (role != null) {
                        logger.info("Rol encontrado: " + role.getName());
                    }
                })
                .doOnError(error -> logger.log(Level.SEVERE, "Error al buscar rol con ID " + id + ": " + error.getMessage()))
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warning("Rol no encontrado con ID: " + id);
                    return Mono.error(new RoleNotFoundException(ErrorCode.ROLE_NOT_FOUND));
                }));
    }

    public Flux<Role> findAllRoles() {
        logger.info("Obteniendo todos los roles");
        return roleRepository.findAll()
                .doOnComplete(() -> logger.info("Todos los roles obtenidos exitosamente"))
                .doOnError(error -> logger.log(Level.SEVERE, "Error al obtener roles: " + error.getMessage()));
    }

    public Mono<Role> createRole(Role role) {
        logger.info("Creando nuevo rol: " + role.getName());
        return roleRepository.save(role)
                .doOnSuccess(savedRole ->
                        logger.info("Rol creado exitosamente con ID: " + savedRole.getId()))
                .doOnError(error ->
                        logger.log(Level.SEVERE, "Error al crear rol " + role.getName() + ": " + error.getMessage()));
    }
}