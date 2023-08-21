package sapo.vn.ex7restfulapispring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sapo.vn.ex7restfulapispring.dto.UserDTO;
import sapo.vn.ex7restfulapispring.entity.Role;
import sapo.vn.ex7restfulapispring.entity.User;
import sapo.vn.ex7restfulapispring.exception.BaseException;
import sapo.vn.ex7restfulapispring.repository.RoleRepository;
import sapo.vn.ex7restfulapispring.repository.UserRepository;
import sapo.vn.ex7restfulapispring.service.UserService;
import sapo.vn.ex7restfulapispring.utils.BaseResponseDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public BaseResponseDTO registerAccount(UserDTO userDTO) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        validateAccount(userDTO);

        User user = insertUser(userDTO);

        try {
            userRepository.save(user);
            responseDTO.setCode(String.valueOf(HttpStatus.OK.value()));
            responseDTO.setMessage("Create account successfully!");
        } catch (Exception e) {
            responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            responseDTO.setMessage("Service unavailable");
        }
        return null;
    }

    private User insertUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(userDTO.getRole()));
        user.setRoles(roles);
        return user;
    }

    private void validateAccount(UserDTO userDTO) {
        if (ObjectUtils.isEmpty(userDTO)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Data must not empty!");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());
        if (!ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Username had existed!");
        }

        List<String> roles = roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
        if (!roles.contains(userDTO.getRole())) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }
    }
}
