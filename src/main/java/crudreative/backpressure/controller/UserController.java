package crudreative.backpressure.controller;


import crudreative.backpressure.entity.User;
import crudreative.backpressure.exception.EmailUniquenessException;
import crudreative.backpressure.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

/*   @PostMapping
*   public Mono<User> createUser (@RequestBody User user){
*        return userRepository.save(user);
*    }
*/

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<User>  getUserById (@PathVariable String id){
        return userRepository.findById(id);
    }

    @PutMapping
    public  Mono<User> updateUser (@RequestBody User user){
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser (@PathVariable String id){
        return userRepository.deleteById(id);
    }

    //post advanced

    @PostMapping
    public  Mono<ResponseEntity<User>> createUser (@RequestBody User user){
        return userRepository.findByEmail(user.email())
                .flatMap(existingUser -> Mono.error(new EmailUniquenessException("Email already exists!")))
                .then(userRepository.save(user)) // save the new user if the email does not exist
                .map(ResponseEntity::ok) //Map the saved user to a ResponseEntity
                .doOnNext(savedUser -> System.out.println("New User created : " + savedUser)
                ) // Logging or further action
                .onErrorResume(e -> {
                    //Handling error such as email uniqueness violation
                    System.out.println("An exception has occurred : " + e.getMessage());
                    if (e instanceof  EmailUniquenessException) {
                        return Mono.just(ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build());
                    }
                    else{
                        return Mono.just(ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .build());
                    }
                });
    }

    @GetMapping("/stream")
    public Flux<User> streamUsers() {
        long start = System.currentTimeMillis();
        return userRepository.findAll()
                .onBackpressureBuffer()  // Buffer strategy for back-pressure
                .doOnNext(user -> log.debug("Processed User: {} in {} ms", user.name(), System.currentTimeMillis() - start))
                .doOnError(error -> log.error("Error streaming users", error))
                .doOnComplete(() -> log.info("Finished streaming users for streamUsers in {} ms", System.currentTimeMillis() - start));
    }
}
