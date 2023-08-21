package sapo.vn.ex7restfulapispring.config.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class này để xử lý vấn đề về Cross-Origin Resource Sharing (CORS)
 */
@Configuration
public class CorsFiler implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // Ép kiểu đối tượng ServletResponse thành HttpServletResponse, để có thể sử dụng các phương thức đặc biệt cho phản hồi HTTP.
        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //Chấp nhận tất cả các nguồn (origin) để truy cập tài nguyên từ server
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // Cho phép bất kỳ phương thức HTTP nào (GET, POST, PUT, DELETE, v.v.) trong yêu cầu.
        httpServletResponse.setHeader("Access-Control-Allow-Method", "*");
        //Cho phép bất kỳ tiêu đề (header) HTTP nào trong yêu cầu.
        //Cho phép gửi các tiêu đề tùy chỉnh hoặc tiêu đề bổ sung trong yêu cầu.
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        //Thiết lập thời gian tối đa (trong mili giây) mà trình duyệt được lưu trữ tiêu đề CORS để không cần gửi yêu cầu kiểm tra trước (preflight) nữa
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");

        //được sử dụng để kiểm tra xem server có hỗ trợ CORS hay không và xem trình duyệt có quyền truy cập vào nguồn không
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }
        //Nếu không phải là phương thức OPTIONS, tiếp tục xử lý các yêu cầu tiếp theo trong chuỗi lọc bằng cách gọi filterChain.doFilter.
        // Các yêu cầu sau này sẽ không bị chặn bởi CORS và được xử lý bình thường bởi các Filter và Servlet tiếp theo
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
