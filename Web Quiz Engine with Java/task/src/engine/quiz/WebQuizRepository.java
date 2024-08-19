package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WebQuizRepository extends CrudRepository<WebQuiz, Integer>,
        PagingAndSortingRepository<WebQuiz, Integer> {
    List<WebQuiz> findAll();
    Page<WebQuiz> findAll(Pageable pageable);
}
