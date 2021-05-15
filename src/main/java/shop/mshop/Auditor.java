package shop.mshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import shop.mshop.domain.Member;

import java.util.Optional;

public class Auditor implements AuditorAware<String> {



    @Override
    public Optional<String> getCurrentAuditor() {
        // memberId 를 하자.
        // 로그인 구현 후 세션으로 불러오기
        return Optional.of("ㅅ");
    }
}
