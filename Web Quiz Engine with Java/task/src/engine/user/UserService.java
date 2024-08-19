package engine.user;

import engine.dto.UserRequest;
import engine.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    public QuizUser registerNewUser(UserRequest request) {
        if (userRepository.existsByUsername(request.email())) throw new UserExistsException("User already exists");
        return userRepository.save(request.toQuizUser(passwordEncoder));
    }
}
