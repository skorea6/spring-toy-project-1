package shop.mshop.constant;

public class CommonConstant {
    public static final String AES256KEY = "beyondinc20201005secret";
    public static final String ACCESS_TOKEN_URL = "http://localhost:8080/auth/authorize";
    public static final String URL_CAST_API_USER_CASTANDLOGIN_INFO="http://localhost:8080/event/castInfo";

    // --------------------------------------------------------------
    // 메서드타입
    // --------------------------------------------------------------
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_GET = "GET";

    public static final int ERR_REST_TIMEOUT_ERROR = 4009;
    public static final int ERR_REST_ERROR = 4007;
    public static final int ERR_REST_CONNECTION_ERROR = 4008;
    public static final int ERR_INTERNAL_ERROR = 500;

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    // 공통
    public static final int ERR_SUCCESS = 200; // 성공
    public static final int ERR_TIMEOUT = 500; // TimeOut
    public static final int ERR_NOT_FOUND_METHOD = 400; // JSON항목이 올바르지 않음

    // 인증
    public static final int ERR_AUTH_TOKEN_INVALID = 401; // 토큰이 유효하지 않습니다.

    //JSON
    public static final int ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND = 4201; // json필수항목 존재하지 않음
    public static final int ERR_JSON_ATTR_IS_INVALID = 4202; // JSON항목이 올바르지 않음


    //DB
    public static final int ERR_NOT_SEARCH_MATCHED_KEY = 4301; //해당 키로 검색된 데이터가 없음
    public static final int ERR_DATABASE_SQL_TRANSACTION = 500; // DB 트렌젝션 에러
    public static final int ERR_DATABASE_SQL = 501; // DB 에러


    // 로그인

    public static final int ERR_NOT_LOGIN = 5000; // 로그인하지 않음
}
