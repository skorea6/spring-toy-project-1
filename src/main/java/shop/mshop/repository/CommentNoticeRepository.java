package shop.mshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.mshop.domain.CommentNotice;

public interface CommentNoticeRepository extends JpaRepository<CommentNotice, Long> {
    Page<CommentNotice> findAllByNoticeId(Pageable pageable, Long noticeId);
}
