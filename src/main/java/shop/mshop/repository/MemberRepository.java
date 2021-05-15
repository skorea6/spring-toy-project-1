package shop.mshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.mshop.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findBySessionKey(String sessionKey);
}
