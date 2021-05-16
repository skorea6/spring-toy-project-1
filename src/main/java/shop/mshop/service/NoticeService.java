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
import shop.mshop.message.request.NoticeListRequest;
import shop.mshop.message.request.NoticeWriteRequest;
import shop.mshop.message.response.NoticeListResponse;
import shop.mshop.message.response.NoticeReadResponse;
import shop.mshop.repository.NoticeRepository;
import shop.mshop.util.DateUtil;
import shop.mshop.util.Paging;

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

        Notice board = new Notice(findMember, request.getTitle(), request.getContent());
        board.setIpAddress(ipAddress);

        noticeRepository.save(board);
        return board.getId();
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
        Optional<Notice> findNotice = noticeRepository.findById(id);
        if (findNotice.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_REQUIRE_ATTR_IS_NOT_FOUND, "해당 게시물을 찾을 수 없습니다.", null);
        }

        NoticeReadResponse response = new NoticeReadResponse();
        response.setId(findNotice.get().getId());
        response.setTitle(findNotice.get().getTitle());
        response.setContent(findNotice.get().getContent());
        response.setWriterMemberId(findNotice.get().getMember().getMemberId());
        response.setWriterName(findNotice.get().getMember().getName());
        response.setCreatedDate(DateUtil.dateToString(findNotice.get().getCreatedDate(), null));

        return response;
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
