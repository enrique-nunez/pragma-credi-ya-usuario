package co.com.pragma.r2dbc;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.RoleEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RoleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role, RoleEntity, Long, RoleReactiveRepository> implements RoleRepository {

    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Mono<Role> findByName(String name) {
        return repository.findByName(name)
                .map(entity -> mapper.map(entity, Role.class));
    }

    @Override
    public Flux<Role> findAll() {
        return repository.findAll()
                .map(entity -> mapper.map(entity, Role.class));
    }

    @Override
    public Mono<Role> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Mono<Role> save(Role role) {
        return super.save(role);
    }
}