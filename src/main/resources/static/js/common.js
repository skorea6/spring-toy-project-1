function apiErrCode(request){
    var state = request.status;
    var msg = "";

    if(state == 500){
        var responseMessage = JSON.parse(request.responseText);
        if(responseMessage.statusMessage != null){
            msg = "[" + responseMessage.statusCode + "]" + " " + responseMessage.statusMessage;
        }else{
            switch (state){
                case 401 :
                    msg ="토큰이 유효하지 않음";
                    break;
                case 500 :
                    msg ="Server Invalid";
                    break;
                case 4201 :
                    msg ="Json Missing";
                    break;
                case 4301 :
                    msg ="키를 찾을 수 없음";
                    break;
                default :
                    msg ="에러 코드를 확인할 수 없습니다.";
            }
        }

    }else{
        msg ="서버 에러가 발생했습니다. (" + state + ")";
    }

    return msg;
}