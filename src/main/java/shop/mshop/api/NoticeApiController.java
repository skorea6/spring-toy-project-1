package shop.mshop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.mshop.constant.ApiExceptionConstant;
import shop.mshop.message.StatusResponse;
import shop.mshop.message.request.NoticeListRequest;
import shop.mshop.message.request.NoticeReadRequest;
import shop.mshop.message.request.NoticeWriteRequest;
import shop.mshop.message.response.NoticeListResponse;
import shop.mshop.message.response.NoticeReadResponse;
import shop.mshop.message.response.NoticeWriteResponse;
import shop.mshop.service.NoticeService;
import shop.mshop.util.HttpSessionUtils;
import shop.mshop.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class NoticeApiController {

    private final NoticeService noticeService;
    ApiExceptionConstant apiExceptionConstant = new ApiExceptionConstant();


    @PutMapping("/api/v1/notice/write")
    public StatusResponse<NoticeWriteResponse> writeNoticeV1(@RequestBody NoticeWriteRequest request, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getTitle(), "title");
        apiExceptionConstant.checkRequireAttr(request.getContent(), "content");

        // 클라이언트 아이피 주소 가져오기
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Long boardId = noticeService.writeByMemberSession(request, IpAddressUtil.getIp(httpServletRequest), HttpSessionUtils.getMemberFromSession(httpSession));

        return new StatusResponse<>(new NoticeWriteResponse(boardId));
    }

    @PostMapping("/api/v1/notice/list")
    public StatusResponse<NoticeListResponse> listNoticeV1(@RequestBody NoticeListRequest request) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getPageNum(), "pageNum");
        apiExceptionConstant.checkRequireAttr(request.getPageElementCount(), "pageElementCount");
        apiExceptionConstant.checkRequireAttr(request.getPageBlockCount(), "pageBlockCount");
        apiExceptionConstant.checkRequireAttr(request.getSortNm(), "sortNm");
        apiExceptionConstant.checkRequireAttr(request.getSortType(), "sortType");

        NoticeListResponse response = noticeService.list(request);

        return new StatusResponse<>(response);
    }

    @PostMapping("/api/v1/notice/read")
    public StatusResponse<NoticeReadResponse> listNoticeV1(@RequestBody NoticeReadRequest request) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getId(), "id");
        NoticeReadResponse response = noticeService.readById(request.getId());

        return new StatusResponse<>(response);
    }
}
