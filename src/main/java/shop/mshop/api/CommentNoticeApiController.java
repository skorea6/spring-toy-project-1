package shop.mshop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.mshop.constant.ApiExceptionConstant;
import shop.mshop.message.StatusResponse;
import shop.mshop.message.request.CommentNoticeEditRequest;
import shop.mshop.message.request.CommentNoticeListRequest;
import shop.mshop.message.request.CommentNoticeWriteRequest;
import shop.mshop.message.request.NoticeEditRequest;
import shop.mshop.message.response.CommentNoticeEditResponse;
import shop.mshop.message.response.CommentNoticeListResponse;
import shop.mshop.message.response.CommentNoticeWriteResponse;
import shop.mshop.message.response.NoticeEditResponse;
import shop.mshop.service.CommentNoticeService;
import shop.mshop.util.HttpSessionUtils;
import shop.mshop.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class CommentNoticeApiController {

    private final CommentNoticeService commentNoticeService;
    //private final MemberService memberService;

    ApiExceptionConstant apiExceptionConstant = new ApiExceptionConstant();


    @PutMapping("/api/v1/comment/notice/write")
    public StatusResponse<CommentNoticeWriteResponse> writeCommentNoticeV1(@RequestBody CommentNoticeWriteRequest request, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getNoticeId(), "noticeId");
        apiExceptionConstant.checkRequireAttr(request.getComment(), "comment");

        // 클라이언트 아이피 주소 가져오기
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Long commentId = commentNoticeService.writeByMemberSession(request, IpAddressUtil.getIp(httpServletRequest), HttpSessionUtils.getMemberFromSession(httpSession));

        return new StatusResponse<>(new CommentNoticeWriteResponse(commentId));
    }

    @PostMapping("/api/v1/comment/notice/list")
    public StatusResponse<CommentNoticeListResponse> listCommentNoticeV1(@RequestBody CommentNoticeListRequest request, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getNoticeId(), "noticeId");
        apiExceptionConstant.checkRequireAttr(request.getPageNum(), "pageNum");
        apiExceptionConstant.checkRequireAttr(request.getPageElementCount(), "pageElementCount");
        apiExceptionConstant.checkRequireAttr(request.getPageBlockCount(), "pageBlockCount");
        apiExceptionConstant.checkRequireAttr(request.getSortNm(), "sortNm");
        apiExceptionConstant.checkRequireAttr(request.getSortType(), "sortType");

        CommentNoticeListResponse response = commentNoticeService.list(request, httpSession);

        return new StatusResponse<>(response);
    }

    @PutMapping("/api/v1/comment/notice/edit")
    public StatusResponse<CommentNoticeEditResponse> editCommentNoticeV1(@RequestBody CommentNoticeEditRequest request, HttpSession httpSession) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getCommentId(), "commentId");
        apiExceptionConstant.checkRequireAttr(request.getComment(), "comment");

        CommentNoticeEditResponse response = commentNoticeService.editById(request, httpSession);

        return new StatusResponse<>(response);
    }
}
