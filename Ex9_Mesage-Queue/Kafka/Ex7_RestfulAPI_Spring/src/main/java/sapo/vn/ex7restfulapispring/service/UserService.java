package sapo.vn.ex7restfulapispring.service;

import sapo.vn.ex7restfulapispring.dto.UserDTO;
import sapo.vn.ex7restfulapispring.utils.BaseResponseDTO;

public interface UserService {
    BaseResponseDTO registerAccount(UserDTO userDTO);
}
