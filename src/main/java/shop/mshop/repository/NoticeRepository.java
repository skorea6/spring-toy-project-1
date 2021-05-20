package shop.mshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.mshop.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findAll(Pageable pageable);

    Page<Notice> findAllByTitleContaining(Pageable pageable, String title);
    Page<Notice> findAllByContentContaining(Pageable pageable, String content);
    Page<Notice> findAllByMember_MemberId(Pageable pageable, String member_memberId);
    Page<Notice> findAllByMember_NameContaining(Pageable pageable, String member_name);
}
