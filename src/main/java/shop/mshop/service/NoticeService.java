package shop.mshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mshop.constant.CommonConstant;
import shop.mshop.domain.Notice;
import shop.mshop.domain.Member;
import shop.mshop.exception.global.ApiException;
import shop.mshop.message.NoticeList;
import shop.mshop.message.request.NoticeDeleteRequest;
import shop.mshop.message.request.NoticeEditRequest;
import shop.mshop.message.request.NoticeListRequest;
import shop.mshop.message.request.NoticeWriteRequest;
import shop.mshop.message.response.NoticeDeleteResponse;
import shop.mshop.message.response.NoticeEditResponse;
import shop.mshop.message.response.NoticeListResponse;
import shop.mshop.message.response.NoticeReadResponse;
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
public class NoticeService {
    private final MemberService memberService;
    private final NoticeRepository noticeRepository;

    @Transactional
    public Long writeByMemberSession(NoticeWriteRequest request, String ipAddress, String sessionKey) {
        Member findMember = memberService.findMemberBySession(sessionKey);

        // title 은 5글자 이상, 20글자 이하로
        validateLengthTitle(request.getTitle());

        // content 는 5글자 이상, 1000글자 이하로
        validateLengthContent(request.getContent());

        // content 의 줄바꿈을 <br>로 변경경
        request.setContent(request.getContent().replace("\n", "<br>"));

        Notice notice = new Notice(findMember, request.getTitle(), request.getContent());
        notice.setIpAddress(ipAddress);

        noticeRepository.save(notice);
        return notice.getId();
    }

    public NoticeListResponse list(NoticeListRequest request) {
        Sort.Direction sortType;

        if (request.getPageNum() < 1 || request.getPageElementCount() < 1 || request.getPageBlockCount() < 1) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "값이 1보다 작을 수는 없습니다.", null);
        }

        if (request.getSortType().equals("desc")) {
            sortType = Sort.Direction.DESC;
        }else{
            sortType = Sort.Direction.ASC;
        }

        // JPA Paging 시작페이지 기본값이 0 이기 때문에, 1로 맞춰주는 작업을 진행함.

        PageRequest pageRequest = PageRequest.of(request.getPageNum()-1, request.getPageElementCount(), sortType, request.getSortNm());
        Page<Notice> page = noticeRepository.findAll(pageRequest);
        List<NoticeList> content = page
                .map(notice -> new NoticeList(notice.getId(), notice.getTitle(), notice.getMember().getName(), notice.getMember().getMemberId(), DateUtil.dateToString(notice.getCreatedDate(), null)))
                .getContent();

        NoticeListResponse response = new NoticeListResponse();

        response.setPageNum(page.getNumber()+1);
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
        response.setNoticeList(content);

        return response;
    }

    public NoticeReadResponse readById(Long id) {
        Optional<Notice> findNotices = noticeRepository.findById(id);
        if (findNotices.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, "해당 게시물을 찾을 수 없습니다.", null);
        }

        Notice findNotice = findNotices.get();

        NoticeReadResponse response = new NoticeReadResponse();
        response.setId(findNotice.getId());
        response.setTitle(findNotice.getTitle());
        response.setContent(findNotice.getContent());
        response.setWriterMemberId(findNotice.getMember().getMemberId());
        response.setWriterName(findNotice.getMember().getName());
        response.setCreatedDate(DateUtil.dateToString(findNotice.getCreatedDate(), null));

        return response;
    }

    @Transactional
    public NoticeEditResponse editById(NoticeEditRequest request, HttpSession httpSession) {
        Optional<Notice> findNotices = noticeRepository.findById(request.getId());
        if (findNotices.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, "해당 게시물을 찾을 수 없습니다.", null);
        }

        if (!this.isWriterBySession(request.getId(), httpSession)) {
            throw new ApiException(CommonConstant.ERR_NOT_PERMISSION, "권한이 없습니다.", null);
        }

        // title 은 5글자 이상, 20글자 이하로
        validateLengthTitle(request.getTitle());

        // content 는 5글자 이상, 1000글자 이하로
        validateLengthContent(request.getContent());

        // content 의 줄바꿈을 <br>로 변경경
        request.setContent(request.getContent().replace("\n", "<br>"));

        Notice findNotice = findNotices.get();
        findNotice.updateNotice(request.getTitle(), request.getContent());

        NoticeEditResponse response = new NoticeEditResponse(findNotice.getId());
        return response;
    }

    @Transactional
    public NoticeDeleteResponse deleteById(Long id, HttpSession httpSession) {
        Optional<Notice> findNotices = noticeRepository.findById(id);
        if (findNotices.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, "해당 게시물을 찾을 수 없습니다.", null);
        }

        if (!this.isWriterBySession(id, httpSession)) {
            throw new ApiException(CommonConstant.ERR_NOT_PERMISSION, "권한이 없습니다.", null);
        }

        noticeRepository.deleteById(findNotices.get().getId());
        NoticeDeleteResponse response = new NoticeDeleteResponse(findNotices.get().getId());
        return response;
    }

    public Boolean isWriterBySession(Long id, HttpSession httpSession) {
        NoticeReadResponse response = this.readById(id);

        // 작성자 본인 검사
        if (HttpSessionUtils.isLoginUser(httpSession)) {
            Member findMember = memberService.findMemberBySession(HttpSessionUtils.getMemberFromSession(httpSession));
            if (findMember.getMemberId() == response.getWriterMemberId()) {
                response.setWriterCheck(true);
            }
        }

        return response.getWriterCheck();
    }

    private void validateLengthContent(String content) {
        if (content.length() < 5 || content.length() > 1000) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "내용은 5글자 이상 1000글자 이하로 입력하세요.", null);
        }
    }

    private void validateLengthTitle(String title) {
        if (title.length() < 5 || title.length() > 20) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "제목은 5글자 이상 20글자 이하로 입력하세요.", null);
        }
    }
}
