package engine.quiz;

import engine.exceptions.ForbiddenException;
import engine.exceptions.NotFoundException;
import engine.user.QuizUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WebQuizService {
    @Autowired
    WebQuizRepository quizRepository;

    @Autowired
    SolutionRepository  solutionRepository;

    public void solveQuiz(WebQuiz quiz, QuizUser quizUser) {
        solutionRepository.save(new Solution(LocalDateTime.now(),quizUser, quiz));
    }

    public Page<Solution> getSolutionsPageForUser(int number, QuizUser quizUser) {
        Pageable pageable = PageRequest.of(number, 10,
                Sort.by(Sort.Direction.DESC, "completedAt"));
        return solutionRepository.findAllByUser(quizUser, pageable);
    }

    public WebQuiz addQuiz(WebQuiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<WebQuiz> getQuizList() {
        return quizRepository.findAll();
    }

    public Optional<WebQuiz> getById(int id) {
        return quizRepository.findById(id);
    }

    public Page<WebQuiz> getPaginatedQuizList(int number) {
        Pageable pageable = PageRequest.of(number, 10);
        return quizRepository.findAll(pageable);
    }

    @Transactional
    public void deleteByIdAndUser(int id, QuizUser user) {
        Optional<WebQuiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isPresent()) {
            WebQuiz quiz = quizOptional.get();
            if (!quiz.getUser().equals(user)) {
                throw new ForbiddenException("Quiz is not owned by user!");
            } else {
                quizRepository.delete(quiz);
            }
        } else {
            throw new NotFoundException("Quiz not found!");
        }
    }
}
