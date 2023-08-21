package sapo.vn.ex7restfulapispring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sapo.vn.ex7restfulapispring.utils.BaseResponseDTO;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDTO> handleBaseException(BaseException e) {
        BaseResponseDTO responseDTO = BaseResponseDTO
                .builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.ok(responseDTO);
    }
}
