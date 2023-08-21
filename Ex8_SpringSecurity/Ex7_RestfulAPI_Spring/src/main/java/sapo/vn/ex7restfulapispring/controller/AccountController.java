package sapo.vn.ex7restfulapispring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sapo.vn.ex7restfulapispring.dto.UserDTO;
import sapo.vn.ex7restfulapispring.service.UserService;
import sapo.vn.ex7restfulapispring.utils.BaseResponseDTO;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerAccount(userDTO));
    }
}
