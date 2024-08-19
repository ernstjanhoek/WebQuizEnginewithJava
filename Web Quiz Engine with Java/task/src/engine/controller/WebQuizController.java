package engine.controller;

import engine.dto.CompletedQuizResponse;
import engine.dto.PaginatedQuizResponse;
import engine.dto.QuizResponse;
import engine.exceptions.NotFoundException;
import engine.quiz.Solution;
import engine.quiz.WebQuiz;
import engine.quiz.WebQuizService;
import engine.user.QuizUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class WebQuizController {
    @Autowired
    WebQuizService webQuizService;

    @PostMapping("/api/quizzes")
    public QuizResponse handleCreateNewQuiz(@Valid @RequestBody QuizRequest request, @AuthenticationPrincipal QuizUser user) {
        List<Integer> answer = new ArrayList<>();
        if (request.answer() != null) {
            answer = request.answer();
        }
        WebQuiz quizObject = webQuizService.addQuiz(new WebQuiz(request.title(), request.text(), request.options(), answer, user));
        return new QuizResponse(quizObject);
    }

    @GetMapping("/api/quizzes/completed")
    public CompletedQuizResponse handleGetCompletedQuizzes(@RequestParam(value = "page") int index,
                                                           @AuthenticationPrincipal QuizUser user) {
        Page<Solution> page = webQuizService.getSolutionsPageForUser(index, user);
        return CompletedQuizResponse.fromPage(page);
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizResponse handleGetQuizById(@PathVariable("id") int id) {
        Optional<WebQuiz> webQuizOptional = webQuizService.getById(id);
        if (webQuizOptional.isPresent()) {
            return new QuizResponse(webQuizOptional.get());
        } else {
            throw new NotFoundException("");
        }
    }

    @GetMapping("/api/quizzes")
    public PaginatedQuizResponse handleGetAllQuizzes(@RequestParam int page) {
        Page<WebQuiz> webQuizPage = webQuizService.getPaginatedQuizList(page);
        return PaginatedQuizResponse.fromPage(webQuizPage);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public PostQuizResponse handlePostQuiz(@PathVariable("id") int id, @RequestBody AnswerRequest request, @AuthenticationPrincipal QuizUser user) {
        Optional<WebQuiz> webQuizOptional = webQuizService.getById(id);
        if (webQuizOptional.isEmpty()) {
            throw new NotFoundException("");
        } else {
            if (webQuizOptional.get().isCorrectSolution(request.answer())) {
                webQuizService.solveQuiz(webQuizOptional.get(), user);
                return new PostQuizResponse(true, "Congratulations, you're right!");
            } else {
                return new PostQuizResponse(false, "Wrong answer! Please, try again.");
            }
        }
    }

    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable int id, @AuthenticationPrincipal QuizUser user) {
        webQuizService.deleteByIdAndUser(id, user);
    }

    public record AnswerRequest(List<Integer> answer) {}

    public record QuizRequest(@Valid @NotBlank String title, @Valid @NotBlank String text, @Valid @NotNull @Size(min = 2) List<String> options, List<Integer> answer) {}

    public record PostQuizResponse(boolean success, String feedback) {}
}
