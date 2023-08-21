package sapo.vn.ex7restfulapispring.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
