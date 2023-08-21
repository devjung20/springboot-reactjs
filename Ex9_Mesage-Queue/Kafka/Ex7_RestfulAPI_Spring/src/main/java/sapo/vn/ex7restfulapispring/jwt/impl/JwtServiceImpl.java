package sapo.vn.ex7restfulapispring.jwt.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sapo.vn.ex7restfulapispring.exception.BaseException;
import sapo.vn.ex7restfulapispring.jwt.JwtConfig;
import sapo.vn.ex7restfulapispring.jwt.JwtService;
import sapo.vn.ex7restfulapispring.service.security.UserDetailsCustom;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;

    /**
     * Phương thức này được sử dụng để trích xuất thông tin từ một chuỗi JSON Web Token (JWT)
     */
    @Override
    public Claims extractClaims(String token) {
        return Jwts
                //Được sử dụng để cấu hình và tạo trình phân tích (parser) cho JWT
                .parserBuilder()
                //Phương thức này thiết lập khóa ký (signing key) cho trình phân tích JWT
                .setSigningKey(getKey())
                .build() //Phương thức này hoàn thành cấu hình của JwtParserBuilder và trả về một JwtParser
                //Phương thức này trích xuất thông tin từ chuỗi JWT (được đại diện bởi token) bằng cách sử dụng JwtParser.
                //Nó sẽ xác thực chữ ký và kiểm tra tính hợp lệ của JWT.
                .parseClaimsJws(token)
                //Phương thức này trả về đối tượng Claims, chứa các thông tin từ JWT (payload của JWT).
                // Đối tượng Claims bao gồm các thông tin như tên người dùng, vai trò, thời gian hết hạn, v.v.
                // Nó có thể được sử dụng để xác thực và kiểm tra quyền truy cập của người dùng trong ứng dụng
                .getBody();
    }

    /**
     * Phương thức này được sử dụng để nhận và trả về một đối tượng khóa (Key)
     * dùng để ký và xác thực JSON Web Tokens (JWT) bằng thuật toán HMAC-SHA
     */
    @Override
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Phương thức này được sử dụng để tạo ra JSON Web Token (JWT) từ thông tin của đối tượng UserDetailsCustom,
     * bao gồm tên người dùng, vai trò, trạng thái kích hoạt, thời gian phát hành và hết hạn.
     */
    @Override
    public String generateToken(UserDetailsCustom userDetailsCustom) {
        Instant now = Instant.now();
        List<String> roles = new ArrayList<>();

        userDetailsCustom.getAuthorities().forEach(role -> {
            roles.add(role.getAuthority());
        });

        log.info("Role: {} ", roles);

        return Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .claim("authorities", userDetailsCustom.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("roles", roles)
                .claim("isEnable", userDetailsCustom.isEnabled())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        final String username = extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return !ObjectUtils.isEmpty(userDetails);
    }

    private String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token expiration!");
        } catch (UnsupportedJwtException e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token not support!");
        } catch (MalformedJwtException e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format 3 part of token!");
        } catch (SignatureException e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format token!");
        } catch (Exception e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), e.getLocalizedMessage());
        }

        return claims;
    }
}
