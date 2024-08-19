package engine.controller;


import engine.dto.UserRequest;
import engine.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Validated
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody UserRequest request) {
        userService.registerNewUser(request);
    }
}
