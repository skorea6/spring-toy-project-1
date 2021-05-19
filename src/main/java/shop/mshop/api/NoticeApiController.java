package shop.mshop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.mshop.constant.ApiExceptionConstant;
import shop.mshop.message.StatusResponse;
import shop.mshop.message.request.*;
import shop.mshop.message.response.*;
import shop.mshop.service.NoticeService;
import shop.mshop.util.HttpSessionUtils;
import shop.mshop.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class NoticeApiController {

    private final NoticeService noticeService;
    //private final MemberService memberService;

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

    @GetMapping("/api/v1/notice/read/{noticeId}")
    public StatusResponse<NoticeReadResponse> readNoticeV1(@PathVariable("noticeId") Long noticeId, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(noticeId, "noticeId");

        NoticeReadResponse response = noticeService.readById(noticeId, httpSession);

        return new StatusResponse<>(response);
    }

    @PutMapping("/api/v1/notice/edit")
    public StatusResponse<NoticeEditResponse> editNoticeV1(@RequestBody NoticeEditRequest request, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getId(), "id");
        apiExceptionConstant.checkRequireAttr(request.getTitle(), "title");
        apiExceptionConstant.checkRequireAttr(request.getContent(), "content");

        NoticeEditResponse response = noticeService.editById(request, httpSession);

        return new StatusResponse<>(response);
    }

    @GetMapping("/api/v1/notice/delete/{noticeId}")
    public StatusResponse<NoticeDeleteResponse> deleteNoticeV1(@PathVariable("noticeId") Long noticeId, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(noticeId, "noticeId");
        NoticeDeleteResponse response = noticeService.deleteById(noticeId, httpSession);

        return new StatusResponse<>(response);
    }

}
