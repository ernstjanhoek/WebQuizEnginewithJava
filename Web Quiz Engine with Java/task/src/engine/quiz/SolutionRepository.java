package engine.quiz;

import engine.user.QuizUser;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolutionRepository extends CrudRepository<Solution, Long>,
        PagingAndSortingRepository<Solution, Long> {
    Page<Solution> findAllByUser(@NonNull QuizUser user, Pageable pageable);
}
