package sapo.vn.ex7restfulapispring.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class HelperUtils {
    /**
     * chuyển đổi các đối tượng Java thành chuỗi JSON có định dạng đẹp (pretty format)
     */
    public static final ObjectWriter JSON_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();
}
