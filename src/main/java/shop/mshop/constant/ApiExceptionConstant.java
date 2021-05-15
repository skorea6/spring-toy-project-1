package shop.mshop.constant;

import org.springframework.util.StringUtils;
import shop.mshop.exception.global.ApiException;

import java.util.List;
import java.util.Map;

import static shop.mshop.util.BaseUtil.isEmpty;

public class ApiExceptionConstant {
    // 필수값 확인
    public void checkRequireAttr(Object attr, String name){
        if(isEmpty(attr) == true){
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, name + " 입력이 필요합니다.",null);
        }
    }

}
