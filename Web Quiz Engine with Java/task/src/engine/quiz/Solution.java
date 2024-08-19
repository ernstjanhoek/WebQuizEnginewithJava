package engine.quiz;

import engine.user.QuizUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Solution {
    public Solution(LocalDateTime completedAt, QuizUser quizUser, WebQuiz webQuiz) {
        this.completedAt = completedAt;
        this.quiz = webQuiz;
        this.user = quizUser;
    }

    @GeneratedValue
    @Id
    long id;

    private @NonNull LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private QuizUser user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private WebQuiz quiz;
}
