package sapo.vn.ex7restfulapispring.jwt;

import io.jsonwebtoken.Claims;
import sapo.vn.ex7restfulapispring.service.security.UserDetailsCustom;

import java.security.Key;

public interface JwtService {
    /**
     * Phương thức này nhận một chuỗi token làm tham số và trả về đối tượng Claims.
     * Claims là một đối tượng chứa thông tin giải mã từ JWT,
     * chẳng hạn như các thông tin về người dùng, quyền truy cập và các trường khác mà bạn đã đính kèm trong JWT
     */
    Claims extractClaims(String token);

    /**
     * Phương thức này trả về đối tượng Key,
     * thường là một đối tượng chứa thông tin về khóa bí mật (secret key) để ký và xác thực JWT
     */
    Key getKey();

    /**
     * Phương thức này được sử dụng để tạo ra một JWT mới từ thông tin của đối tượng UserDetailsCustom,
     * chẳng hạn như tên người dùng, vai trò, thời gian hết hạn, v.v.
     */
    String generateToken(UserDetailsCustom userDetailsCustom);

    /**
     * Kiểm tra token có hợp lệ hay không
     */
    boolean isValidToken(String token);
}
