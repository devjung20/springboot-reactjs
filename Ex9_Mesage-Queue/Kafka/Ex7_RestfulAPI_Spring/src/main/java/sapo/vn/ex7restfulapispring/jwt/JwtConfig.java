package sapo.vn.ex7restfulapispring.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${jwt.url:/login}")
    //sử dụng để lưu trữ đường dẫn URL mặc định khi thực hiện xác thực JWT
    private String url;
    @Value("${jwt.header:Authorization}")
    //Thuộc tính header chứa tên tiêu đề (header) trong yêu cầu HTTP mà chứa chuỗi JWT
    private String header;
    @Value("${jwt.prefix:Bearer}")
    //Thuộc tính prefix chứa tiền tố (prefix) của chuỗi JWT trong tiêu đề yêu cầu HTTP
    private String prefix;
    @Value("${jwt.expiration:#{60*60*1000}}")
    //Thuộc tính expiration lưu trữ thời gian hết hạn (expiration time) của JWT tính bằng mili giây (1h sẽ hết hạn)
    private int expiration;
    @Value("${jwt.secret:c4faf033589269d6d1882b324338a5572cb72f5218d28be8d9ec29ef16275d74}")
    //Thuộc tính secret chứa chuỗi bí mật (secret key) dùng để ký và xác thực JWT.
    private String secret;
}
