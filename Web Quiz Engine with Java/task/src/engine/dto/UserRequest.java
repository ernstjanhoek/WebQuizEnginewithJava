package engine.dto;


import engine.user.QuizUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserRequest(
        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email should be valid")
        @NotBlank String email, @NotBlank @Size(min = 5) String password) {
    public QuizUser toQuizUser(PasswordEncoder encoder) {
        return new QuizUser(encoder.encode(this.password), email, "USER");
    }
}

