package crudreative.backpressure;

import crudreative.backpressure.controller.UserController;
import crudreative.backpressure.entity.User;
import crudreative.backpressure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.oauth2.client.autoconfigure.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserController.class,
        excludeAutoConfiguration = {ReactiveOAuth2ClientAutoConfiguration.class,
                ReactiveOAuth2ClientAutoConfiguration.class})
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Mock
    private UserRepository userRepository;
    private User testUser;
    @Mock
    private SecurityWebFilterChain securityWebFilterChain;
    @BeforeEach
    void setUp() {
        testUser = new User(1L, "Test User", "test@example.com");
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(Flux.just(testUser));

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(1);
    }
    @Test
    void getUserByIdTest() {
        when(userRepository.findById("1")).thenReturn(Mono.just(testUser));
        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }
    @Test
    void createUserTest() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }
    @Test
    void deleteUserTest() {
        when(userRepository.deleteById("1")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void createUserWithExistingEmailTest() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Mono.just(testUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testUser)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void streamUsersTest() {
        when(userRepository.findAll()).thenReturn(Flux.just(testUser));
        webTestClient.get().uri("/users/stream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(1);
    }
}
