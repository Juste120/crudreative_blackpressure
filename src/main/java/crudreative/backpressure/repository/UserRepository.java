package crudreative.backpressure.repository;

import crudreative.backpressure.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
public interface UserRepository extends R2dbcRepository<User,String> {

    Mono<User>findByEmail(String email);
}
