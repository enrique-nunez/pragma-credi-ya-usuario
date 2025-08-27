package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        // Usar lenient() para evitar UnnecessaryStubbingException
        lenient().when(mapper.map(any(UserEntity.class), eq(User.class))).thenReturn(user);
        lenient().when(mapper.map(any(User.class), eq(UserEntity.class))).thenReturn(userEntity);
    }

    @Test
    void mustFindValueById() {
        UserEntity userEntity = createUserEntity();

        when(repository.findById(1L)).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        UserEntity userEntity = createUserEntity();

        when(repository.findAll()).thenReturn(Flux.just(userEntity));

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        when(repository.findAll(any(Example.class))).thenReturn(Flux.just(userEntity));

        Flux<User> result = repositoryAdapter.findByExample(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        when(repository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindByCorreoElectronico() {
        UserEntity userEntity = createUserEntity();

        when(repository.findByEmail("test@example.com")).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findByEmail("test@example.com");

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getEmail().equals("test@example.com"))
                .verifyComplete();
    }

    private UserEntity createUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setFirstName("Test");
        entity.setLastName("User");
        entity.setEmail("test@example.com");
        entity.setPhoneNumber("123456789");
        entity.setAddress("Test Address");
        entity.setBirthDate(LocalDate.of(1990, 1, 1));
        entity.setBaseSalary(BigDecimal.valueOf(50000));
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .phone("123456789")
                .address("Test Address")
                .birthDate(LocalDate.of(1990, 1, 1))
                .baseSalary(BigDecimal.valueOf(50000))
                .creationDate(LocalDateTime.now())
                .build();
    }
}