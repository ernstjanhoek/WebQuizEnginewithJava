package engine.dto;

import engine.quiz.WebQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public record PaginatedQuizResponse(
        int totalPages,
        int totalElements,
        boolean last,
        boolean first,
        Sort sort,
        int number,
        int numberOfElements,
        int size,
        boolean empty,
        Pageable pageable,
        List<QuizResponse> content
) {
    public static PaginatedQuizResponse fromPage(Page<WebQuiz> page) {
        List<QuizResponse> quizResponseList = page.stream().map(QuizResponse::new).toList();
        return new PaginatedQuizResponse(
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.isLast(),
                page.isFirst(),
                page.getSort(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getSize(),
                page.isEmpty(),
                page.getPageable(),
                quizResponseList
        );
    }
}
