package engine.dto;

import engine.quiz.Solution;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record CompletedQuizResponse(
        int totalPages,
        int totalElements,
        boolean last,
        boolean first,
        boolean empty,
        List<CompletedQuiz> content
) {
    public static CompletedQuizResponse fromPage(Page<Solution> page) {
        List<CompletedQuiz> completedQuizs = page.stream()
                .map(s -> new CompletedQuiz(s.getQuiz().getId(), s.getCompletedAt()))
                .toList();
        return new CompletedQuizResponse(
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.isLast(),
                page.isFirst(),
                page.isEmpty(),
                completedQuizs
        );
    }
}

record CompletedQuiz(
        int id,
        LocalDateTime completedAt
) {}