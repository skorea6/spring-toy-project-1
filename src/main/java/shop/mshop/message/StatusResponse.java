package shop.mshop.message;

import lombok.Data;
import shop.mshop.constant.CommonConstant;
import shop.mshop.util.DateUtil;

import java.io.Serializable;

@Data
public class StatusResponse<T> implements Serializable {
    private int statusCode;
    private String statusMessage;
    private String responseTime;
    private T data;

    public StatusResponse(int statusCode, String statusMessage) {
        setData(statusCode, statusMessage, null);
    }

    public StatusResponse(int statusCode, String statusMessage, T data) {
        setData(statusCode, statusMessage, data);
    }

    public StatusResponse(T data) {
        /* 정상응답 가정 초기값 set */
        this.statusCode = CommonConstant.ERR_SUCCESS;
        this.statusMessage = "success";
        this.responseTime = DateUtil.getCurrentTime();
        this.data = data;
    }

    public StatusResponse() {
        /* 정상응답 가정 초기값 set */
        this.statusCode = CommonConstant.ERR_SUCCESS;
        this.statusMessage = "success";
        this.responseTime = DateUtil.getCurrentTime();
        this.data = null;
    }

    public void setData(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.responseTime = DateUtil.getCurrentTime();
        this.data = null;
    }

    public void setData(int statusCode, String statusMessage, T data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.responseTime = DateUtil.getCurrentTime();
        this.data = data;
    }

}
