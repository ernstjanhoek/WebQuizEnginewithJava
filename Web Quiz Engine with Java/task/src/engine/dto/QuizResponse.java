package engine.dto;

import engine.quiz.WebQuiz;

import java.util.List;

public record QuizResponse(int id, String title, String text, List<String> options) {
    public QuizResponse(WebQuiz quiz) {
        this(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions());
    }
}
