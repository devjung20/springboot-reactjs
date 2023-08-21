package sapo.vn.ex8sqlinjection.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sapo.vn.ex8sqlinjection.entity.User;
import sapo.vn.ex8sqlinjection.response.UserResponse;
import sapo.vn.ex8sqlinjection.service.UserService;

@RestController
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login-attacks")
    public UserResponse loginAttacks(@RequestBody User user) {
        return userService.loginAttacks(user);
    }

    @PostMapping("/login-against-attacks")
    public UserResponse loginAgainstAttacks(@RequestBody User user) {
        return userService.loginAgainstAttacks(user);
    }
}
