package shop.mshop.exception;

import lombok.Data;
import shop.mshop.util.DateUtil;

@Data
public class CommonApiRestControllerAdviceVO {
	private int statusCode;
	private String statusMessage;
	private String responseTime;
	private Object data;

	public CommonApiRestControllerAdviceVO(int statusCode, String statusMessage, Object data) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.data = data;
		this.responseTime = DateUtil.getCurrentTime();
	}
}
