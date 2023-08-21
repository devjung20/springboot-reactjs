package sapo.vn.ex7restfulapispring.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import sapo.vn.ex7restfulapispring.entity.User;
import sapo.vn.ex7restfulapispring.exception.BaseException;
import sapo.vn.ex7restfulapispring.repository.UserRepository;

import java.util.stream.Collectors;

public class UserDetailsServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Phương thức này được sử dụng để tải thông tin người dùng từ nguồn dữ liệu (chẳng hạn như cơ sở dữ liệu)
     * dựa trên tên người dùng (username) cung cấp và trả về một đối tượng UserDetails mô tả người dùng hoặc thông
     * báo lỗi nếu không tìm thấy tài khoản người dùng.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = getUserDetails(username);
        if (ObjectUtils.isEmpty(userDetailsCustom)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid username or password!");
        }
        return userDetailsCustom;
    }

    /**
     * phương thức getUserDetails này được sử dụng để tải thông tin người dùng từ cơ sở dữ liệu và chuyển đổi
     * thành một đối tượng UserDetailsCustom chứa các thông tin chi tiết về người dùng và vai trò của họ
     */
    private UserDetailsCustom getUserDetails(String username) {
        User user = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid username or password!");
        }
        return new UserDetailsCustom(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role ->
                        new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
