package engine.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<QuizUser, Integer> {
    Optional<QuizUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
