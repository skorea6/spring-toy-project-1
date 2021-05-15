package shop.mshop.exception.global;

import lombok.Data;
import shop.mshop.util.DateUtil;

@Data
public class ApiRestControllerAdviceVO {
    private int statusCode;
    private String statusMessage;
    private String responseTime;
    private Object data;

    public ApiRestControllerAdviceVO(int statusCode, String statusMessage, Object data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
        this.responseTime = DateUtil.getCurrentTime();
    }
}
