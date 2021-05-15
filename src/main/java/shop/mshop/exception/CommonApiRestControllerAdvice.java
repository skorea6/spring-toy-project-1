package shop.mshop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.mshop.constant.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;

public class CommonApiRestControllerAdvice {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onDataAccessException(HttpServletRequest req, HttpServletResponse response, DataAccessException e) {
		logger.error("Detected onDataAccessException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(600, "데이터 처리 오류 발생", null);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onDuplicateKeyException(HttpServletRequest req, HttpServletResponse response, DuplicateKeyException e) {
		logger.error("Detected onDuplicateKeyException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(600, "데이터 처리 오류 발생", null);
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onBindingException(HttpServletRequest req, HttpServletResponse response, BindException e) {
		logger.error("Detected onBindingException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(601, "데이터 처리 오류 발생", null);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onException(HttpServletRequest req, Exception e) {
		logger.error("Detected onException");
		logger.error("\r\n", e);

		return new CommonApiRestControllerAdviceVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal server error", null);
	}

	//Json 양식에 문제가 있어 에러 발생시킴
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onHttpMessageNotReadableException(HttpServletRequest req, Exception e) {
		logger.error("Detected HttpMessageNotReadableException4202");
		logger.error("\r\n", e);

		return new CommonApiRestControllerAdviceVO(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "Json Form Problem Check Please", null);
	}

	//Json 양식에 문제가 있어 에러 발생시킴
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onHttpRequestMethodNotSupportedException(HttpServletRequest req, Exception e) {
		logger.error("Detected HttpMessageNotReadableException");
		logger.error("\r\n", e);

		return new CommonApiRestControllerAdviceVO(HttpStatus.METHOD_NOT_ALLOWED.value(), "Request method '"+req.getMethod()+"' not supported", null);
	}
}
