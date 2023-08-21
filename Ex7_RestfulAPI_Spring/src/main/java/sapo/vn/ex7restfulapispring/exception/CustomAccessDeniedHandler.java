package sapo.vn.ex7restfulapispring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import sapo.vn.ex7restfulapispring.utils.BaseResponseDTO;
import sapo.vn.ex7restfulapispring.utils.HelperUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Hàm xử lý các trường hợp người dùng không có quyền truy cập vào một tài nguyên hoặc thực hiện một hành động cụ thể
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setMessage("You don't have permission to access this resource");
        responseDTO.setCode(String.valueOf(HttpStatus.FORBIDDEN.value()));
        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
