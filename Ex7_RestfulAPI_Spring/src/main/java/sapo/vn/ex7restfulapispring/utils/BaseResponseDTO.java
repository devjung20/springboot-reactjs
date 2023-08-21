package sapo.vn.ex7restfulapispring.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseResponseDTO {
    private String code;
    private String message;
}
