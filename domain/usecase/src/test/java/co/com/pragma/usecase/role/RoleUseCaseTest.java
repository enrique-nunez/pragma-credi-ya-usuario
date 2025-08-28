package co.com.pragma.usecase.role;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.exceptions.RoleNotFoundException;
import co.com.pragma.model.role.gateways.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleUseCase roleUseCase;
    private Role testRole;
    private List<Role> testRoles;

    @BeforeEach
    void setUp() {
        roleUseCase = new RoleUseCase(roleRepository);

        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("ADMIN");
        testRole.setDescription("Administrator role");

        Role employeeRole = new Role();
        employeeRole.setId(2L);
        employeeRole.setName("EMPLOYEE");
        employeeRole.setDescription("Employee role");

        testRoles = Arrays.asList(testRole, employeeRole);
    }

    @Test
    void findRoleById_ValidId_ShouldReturnRole() {
        // Given
        when(roleRepository.findById(1L)).thenReturn(Mono.just(testRole));

        // When & Then
        StepVerifier.create(roleUseCase.findRoleById(1L))
                .expectNext(testRole)
                .verifyComplete();
    }

    @Test
    void findRoleById_NonExistentId_ShouldThrowRoleNotFoundException() {
        // Given
        when(roleRepository.findById(999L)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(roleUseCase.findRoleById(999L))
                .expectError(RoleNotFoundException.class)
                .verify();
    }

    @Test
    void findRoleById_RepositoryError_ShouldPropagateError() {
        // Given
        RuntimeException repositoryError = new RuntimeException("Database error");
        when(roleRepository.findById(1L)).thenReturn(Mono.error(repositoryError));

        // When & Then
        StepVerifier.create(roleUseCase.findRoleById(1L))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void findAllRoles_Success_ShouldReturnAllRoles() {
        // Given
        when(roleRepository.findAll()).thenReturn(Flux.fromIterable(testRoles));

        // When & Then
        StepVerifier.create(roleUseCase.findAllRoles())
                .expectNext(testRole)
                .expectNext(testRoles.get(1))
                .verifyComplete();
    }

    @Test
    void findAllRoles_EmptyResult_ShouldReturnEmptyFlux() {
        // Given
        when(roleRepository.findAll()).thenReturn(Flux.empty());

        // When & Then
        StepVerifier.create(roleUseCase.findAllRoles())
                .verifyComplete();
    }

    @Test
    void findAllRoles_RepositoryError_ShouldPropagateError() {
        // Given
        RuntimeException repositoryError = new RuntimeException("Database error");
        when(roleRepository.findAll()).thenReturn(Flux.error(repositoryError));

        // When & Then
        StepVerifier.create(roleUseCase.findAllRoles())
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void createRole_ValidRole_ShouldReturnSavedRole() {
        // Given
        Role newRole = new Role();
        newRole.setName("MANAGER");
        newRole.setDescription("Manager role");

        Role savedRole = new Role();
        savedRole.setId(3L);
        savedRole.setName("MANAGER");
        savedRole.setDescription("Manager role");

        when(roleRepository.save(any(Role.class))).thenReturn(Mono.just(savedRole));

        // When & Then
        StepVerifier.create(roleUseCase.createRole(newRole))
                .expectNext(savedRole)
                .verifyComplete();
    }

    @Test
    void createRole_RepositoryError_ShouldPropagateError() {
        // Given
        Role newRole = new Role();
        newRole.setName("MANAGER");
        newRole.setDescription("Manager role");

        RuntimeException repositoryError = new RuntimeException("Save failed");
        when(roleRepository.save(any(Role.class))).thenReturn(Mono.error(repositoryError));

        // When & Then
        StepVerifier.create(roleUseCase.createRole(newRole))
                .expectError(RuntimeException.class)
                .verify();
    }
}
