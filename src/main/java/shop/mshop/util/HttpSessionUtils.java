package shop.mshop.util;

import shop.mshop.constant.CommonConstant;
import shop.mshop.domain.Member;
import shop.mshop.exception.global.ApiException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUserKey";

    public static void setLoginSession(HttpSession httpSession, String sessionKey) {
        httpSession.setAttribute(USER_SESSION_KEY, sessionKey);
    }

    public static void setLogoutSession(HttpSession httpSession) {
        httpSession.removeAttribute(USER_SESSION_KEY);
    }

    public static boolean isLoginUser(HttpSession httpSession) {
        Object sessionedUser = httpSession.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static String getMemberFromSession(HttpSession httpSession) {
        if (!isLoginUser(httpSession)) {
            throw new ApiException(CommonConstant.ERR_NOT_LOGIN, "로그인하지 않은 사용자입니다.", null);
        }
        return httpSession.getAttribute(USER_SESSION_KEY).toString();
    }
}
