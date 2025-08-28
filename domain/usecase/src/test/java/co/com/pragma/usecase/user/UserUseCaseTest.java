package co.com.pragma.usecase.user;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exceptions.InvalidInputException;
import co.com.pragma.model.user.exceptions.UserNotFoundException;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private UserUseCase userUseCase;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userRepository, roleRepository);

        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("EMPLOYEE");
        testRole.setDescription("Employee role");

        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Juan");
        testUser.setLastName("Pérez");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));
        testUser.setAddress("Calle 123");
        testUser.setPhone("1234567890");
        testUser.setEmail("juan.perez@email.com");
        testUser.setBaseSalary(new BigDecimal("1000000"));
        testUser.setRoleId(1L);

    }

    @Test
    void registerUser_ValidUser_ShouldRegisterSuccessfully() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(testUser))
                .expectNext(testUser)
                .verifyComplete();
    }

    @Test
    void registerUser_DuplicateEmail_ShouldThrowException() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(testUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(testUser))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void registerUser_InvalidEmail_ShouldThrowException() {
        // Given
        User invalidUser = new User();
        invalidUser.setFirstName("Juan");
        invalidUser.setLastName("Pérez");
        invalidUser.setBirthDate(LocalDate.of(1990, 1, 1));
        invalidUser.setAddress("Calle 123");
        invalidUser.setPhone("1234567890");
        invalidUser.setEmail("invalid-email");
        invalidUser.setBaseSalary(new BigDecimal("1000000"));
        invalidUser.setRoleId(1L);

        // Agregar mocks necesarios
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(invalidUser))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void registerUser_EmptyFirstName_ShouldThrowException() {
        // Given
        User invalidUser = new User();
        invalidUser.setFirstName("");
        invalidUser.setLastName("Pérez");
        invalidUser.setBirthDate(LocalDate.of(1990, 1, 1));
        invalidUser.setAddress("Calle 123");
        invalidUser.setPhone("1234567890");
        invalidUser.setEmail("test@email.com");
        invalidUser.setBaseSalary(new BigDecimal("1000000"));
        invalidUser.setRoleId(1L);

        // Mocks necesarios para evitar NullPointerException
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(invalidUser))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void registerUser_NullLastName_ShouldThrowException() {
        // Given
        User invalidUser = new User();
        invalidUser.setFirstName("Juan");
        invalidUser.setLastName(null);
        invalidUser.setBirthDate(LocalDate.of(1990, 1, 1));
        invalidUser.setAddress("Calle 123");
        invalidUser.setPhone("1234567890");
        invalidUser.setEmail("test@email.com");
        invalidUser.setBaseSalary(new BigDecimal("1000000"));
        invalidUser.setRoleId(1L);

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(invalidUser))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void registerUser_InvalidSalaryRange_ShouldThrowException() {
        // Given
        User invalidUser = new User();
        invalidUser.setFirstName("Juan");
        invalidUser.setLastName("Pérez");
        invalidUser.setBirthDate(LocalDate.of(1990, 1, 1));
        invalidUser.setAddress("Calle 123");
        invalidUser.setPhone("1234567890");
        invalidUser.setEmail("test@email.com");
        invalidUser.setBaseSalary(new BigDecimal("20000000"));
        invalidUser.setRoleId(1L);

        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.registerUser(invalidUser))
                .expectError(InvalidInputException.class)
                .verify();
    }

    @Test
    void findUserById_ExistingUser_ShouldReturnUserWithRole() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(Mono.just(testUser));
        when(roleRepository.findById(anyLong())).thenReturn(Mono.just(testRole));

        // When & Then
        StepVerifier.create(userUseCase.findUserById(1L))
                .expectNextMatches(user ->
                        user.getId().equals(1L) &&
                                user.getRole() != null &&
                                user.getRole().getName().equals("EMPLOYEE")
                )
                .verifyComplete();
    }

    @Test
    void findUserById_NonExistingUser_ShouldThrowException() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.findUserById(999L))
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void findAllUsers_WithUsers_ShouldReturnUsersWithRoles() {
        // Given
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("María");
        user2.setRoleId(1L);

        when(userRepository.findAll()).thenReturn(Flux.just(testUser, user2));
        when(roleRepository.findById(anyLong())).thenReturn(Mono.just(testRole));

        // When & Then
        StepVerifier.create(userUseCase.findAllUsers())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findUserByEmail_ExistingUser_ShouldReturnUserWithRole() {
        // Given
        when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.findUserByEmail("juan.perez@email.com"))
                .expectNextMatches(user ->
                        user.getEmail().equals("juan.perez@email.com") &&
                                user.getId().equals(1L)
                )
                .verifyComplete();
    }

    @Test
    void findUserByEmail_NonExistingUser_ShouldThrowException() {
        // Given
        when(userRepository.findByEmail("nonexistent@email.com")).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.findUserByEmail("nonexistent@email.com"))
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void findUserByEmail_RepositoryError_ShouldPropagateError() {
        // Given
        RuntimeException repositoryError = new RuntimeException("Database error");
        when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.error(repositoryError));

        // When & Then
        StepVerifier.create(userUseCase.findUserByEmail("juan.perez@email.com"))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void findAllUsers_EmptyResult_ShouldReturnEmptyFlux() {
        // Given
        when(userRepository.findAll()).thenReturn(Flux.empty());

        // When & Then
        StepVerifier.create(userUseCase.findAllUsers())
                .verifyComplete();
    }

    @Test
    void findAllUsers_RepositoryError_ShouldPropagateError() {
        // Given
        RuntimeException repositoryError = new RuntimeException("Database error");
        when(userRepository.findAll()).thenReturn(Flux.error(repositoryError));

        // When & Then
        StepVerifier.create(userUseCase.findAllUsers())
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void findUserById_RoleNotFound_ShouldReturnEmpty() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Mono.just(testUser));
        when(roleRepository.findById(1L)).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(userUseCase.findUserById(1L))
                .verifyComplete(); // Espera que se complete sin emitir elementos
    }

    @Test
    void findUserByEmail_RoleNotFound_ShouldReturnUserWithoutError() {
        // Given
        when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.just(testUser));

        // When & Then
        StepVerifier.create(userUseCase.findUserByEmail("juan.perez@email.com"))
                .expectNextMatches(user -> user.getEmail().equals("juan.perez@email.com"))
                .verifyComplete();
    }
}