package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        when(repository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        when(repository.findAll()).thenReturn(Flux.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

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
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Flux<User> result = repositoryAdapter.findByExample(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindByCorreoElectronico() {
        UserEntity userEntity = createUserEntity();
        User user = createUser();

        when(repository.findByCorreoElectronico("test@example.com")).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findByCorreoElectronico("test@example.com");

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getCorreoElectronico().equals("test@example.com"))
                .verifyComplete();
    }

    private UserEntity createUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setNombres("Test");
        entity.setApellidos("User");
        entity.setCorreoElectronico("test@example.com");
        entity.setTelefono("123456789");
        entity.setDireccion("Test Address");
        entity.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        entity.setSalarioBase(BigDecimal.valueOf(50000));
        entity.setFechaCreacion(LocalDateTime.now());
        return entity;
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .nombres("Test")
                .apellidos("User")
                .correoElectronico("test@example.com")
                .telefono("123456789")
                .direccion("Test Address")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .salarioBase(BigDecimal.valueOf(50000))
                .fechaCreacion(LocalDateTime.now())
                .build();
    }
}
