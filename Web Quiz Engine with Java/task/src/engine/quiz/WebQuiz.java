package engine.quiz;

import engine.user.QuizUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public final class WebQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private @NonNull String title;
    private @NonNull String text;
    @ElementCollection
    private @NonNull List<String> options;
    @ElementCollection
    private @NonNull List<Integer> answer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private @NonNull QuizUser user;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL
    )
    private List<Solution> solutions;

    public boolean isCorrectSolution(List<Integer> solution) {
        return solution.size() == answer.size() &&
                new HashSet<>(solution).containsAll(answer);
    }

    @Override
    public String toString() {
        return "WebQuiz[" +
                "title=" + title + ", " +
                "text=" + text + ", " +
                "options=" + options + ", " +
                "answer=" + answer + ']';
    }
}
