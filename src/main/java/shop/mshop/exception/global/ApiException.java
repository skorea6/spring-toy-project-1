package shop.mshop.exception.global;

import lombok.Data;

@Data
public class ApiException extends RuntimeException{
    private int statusCode;
    private String statusMessage;
    private String responseTime;
    private Object data;

    public ApiException(int statusCode, String statusMessage, Object data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }
}
