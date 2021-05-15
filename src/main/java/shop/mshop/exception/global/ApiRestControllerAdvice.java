package shop.mshop.exception.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mshop.exception.CommonApiRestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

@RestControllerAdvice
public class ApiRestControllerAdvice extends CommonApiRestControllerAdvice {
    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiRestControllerAdviceVO onApiException(HttpServletRequest req, ApiException e) {
        logger.error("Detected onDeliveryException");
        logger.error("\r\n", e);
        return new ApiRestControllerAdviceVO(e.getStatusCode(), e.getStatusMessage(), e.getData());
    }
}
