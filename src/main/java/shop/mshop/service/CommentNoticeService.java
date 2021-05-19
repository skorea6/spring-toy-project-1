package shop.mshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mshop.constant.CommonConstant;
import shop.mshop.domain.CommentNotice;
import shop.mshop.domain.Member;
import shop.mshop.domain.Notice;
import shop.mshop.exception.global.ApiException;
import shop.mshop.message.CommentNoticeList;
import shop.mshop.message.request.CommentNoticeEditRequest;
import shop.mshop.message.request.CommentNoticeListRequest;
import shop.mshop.message.request.CommentNoticeWriteRequest;
import shop.mshop.message.response.*;
import shop.mshop.repository.CommentNoticeRepository;
import shop.mshop.repository.NoticeRepository;
import shop.mshop.util.DateUtil;
import shop.mshop.util.HttpSessionUtils;
import shop.mshop.util.Paging;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentNoticeService {
    private final MemberService memberService;
    private final NoticeRepository noticeRepository;
    private final CommentNoticeRepository commentNoticeRepository;

    @Transactional
    public Long writeByMemberSession(CommentNoticeWriteRequest request, String ipAddress, String sessionKey) {
        Member findMember = memberService.findMemberBySession(sessionKey);
        Notice findNotice = noticeRepository.findById(request.getNoticeId()).get();

        // comment 는 5글자 이상, 1000글자 이하로
        validateLengthComment(request.getComment());

        // comment 의 줄바꿈을 <br>로 변경경
        request.setComment(request.getComment().replace("\n", "<br>"));


        CommentNotice commentNotice = new CommentNotice(findMember, findNotice, request.getComment());
        commentNotice.setIpAddress(ipAddress);

        commentNoticeRepository.save(commentNotice);
        return commentNotice.getId();
    }

    public CommentNoticeListResponse list(CommentNoticeListRequest request, HttpSession httpSession) {
        //request.getId 유효성 검사

        Sort.Direction sortType;

        if (request.getPageNum() < 1 || request.getPageElementCount() < 1 || request.getPageBlockCount() < 1) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "값이 1보다 작을 수는 없습니다.", null);
        }

        if (request.getSortType().equals("desc")) {
            sortType = Sort.Direction.DESC;
        } else {
            sortType = Sort.Direction.ASC;
        }

        // JPA Paging 시작페이지 기본값이 0 이기 때문에, 1로 맞춰주는 작업을 진행함.

        PageRequest pageRequest = PageRequest.of(request.getPageNum() - 1, request.getPageElementCount(), sortType, request.getSortNm());
        Page<CommentNotice> page = commentNoticeRepository.findAllByNoticeId(pageRequest, request.getNoticeId());
        List<CommentNoticeList> content = page
                .map(commentNotice -> new CommentNoticeList(commentNotice.getId(), commentNotice.getComment(), commentNotice.getMember().getName(), commentNotice.getMember().getMemberId(), isWriterBySession(commentNotice.getMember().getId(), httpSession), DateUtil.dateToString(commentNotice.getCreatedDate(), null)))
                .getContent();

        CommentNoticeListResponse response = new CommentNoticeListResponse();

        response.setPageNum(page.getNumber() + 1);
        response.setTotalElements(page.getTotalElements());
        response.setTotalPageCount(page.getTotalPages());
        response.setNumberOfElements(page.getNumberOfElements());

        Paging paging = new Paging();
        paging.setPaging(request.getPageNum(), request.getPageElementCount(), request.getPageBlockCount(), (int) page.getTotalElements());
        response.setFirstPageNum(paging.getFirstPageNum());
        response.setLastPageNum(paging.getLastPageNum());
        response.setPrevBlockPageNum(paging.getPrevBlockPageNum());
        response.setNextBlockPageNum(paging.getNextBlockPageNum());

        response.setFirstPageCheck(page.isFirst());
        response.setLastPageCheck(page.isLast());
        response.setCommentNoticeList(content);

        return response;
    }


    @Transactional
    public CommentNoticeEditResponse editById(CommentNoticeEditRequest request, HttpSession httpSession) {
        Optional<CommentNotice> findCommentNotices = commentNoticeRepository.findById(request.getCommentId());

        if (findCommentNotices.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, "해당 댓글을 찾을 수 없습니다.", null);
        }

        if (!this.isWriterBySession(findCommentNotices.get().getMember().getId(), httpSession)) {
            throw new ApiException(CommonConstant.ERR_NOT_PERMISSION, "권한이 없습니다.", null);
        }

        // comment 는 5글자 이상, 1000글자 이하로
        validateLengthComment(request.getComment());

        // comment 의 줄바꿈을 <br>로 변경경
        request.setComment(request.getComment().replace("\n", "<br>"));

        CommentNotice findCommentNotice = findCommentNotices.get();
        findCommentNotice.updateCommentNotice(request.getComment());

        return new CommentNoticeEditResponse(findCommentNotice.getId());
    }

    @Transactional
    public CommentNoticeDeleteResponse deleteById(Long id, HttpSession httpSession) {
        Optional<CommentNotice> findCommentNotices = commentNoticeRepository.findById(id);
        if (findCommentNotices.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, "해당 댓글을 찾을 수 없습니다.", null);
        }

        if (!this.isWriterBySession(findCommentNotices.get().getMember().getId(), httpSession)) {
            throw new ApiException(CommonConstant.ERR_NOT_PERMISSION, "권한이 없습니다.", null);
        }

        commentNoticeRepository.deleteById(findCommentNotices.get().getId());
        return new CommentNoticeDeleteResponse(findCommentNotices.get().getId());
    }


    public Boolean isWriterBySession(Long id, HttpSession httpSession) {
        Boolean isTrue = false;

        // 작성자 본인 검사
        if (HttpSessionUtils.isLoginUser(httpSession)) {
            Member findMember = memberService.findMemberBySession(HttpSessionUtils.getMemberFromSession(httpSession));
            if (findMember.getId() == id) {
                isTrue = true;
            }
        }

        return isTrue;
    }

    private void validateLengthComment(String comment) {
        if (comment.length() < 5 || comment.length() > 1000) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "댓글은 5글자 이상 1000글자 이하로 입력하세요.", null);
        }
    }
}
