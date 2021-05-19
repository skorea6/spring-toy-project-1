package shop.mshop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import shop.mshop.domain.Address;
import shop.mshop.domain.Member;
import shop.mshop.exception.global.ApiException;
import shop.mshop.service.MemberService;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void loginMember() throws Exception {
        //given
        Member member = new Member("talk105", passwordEncoder.encode("password1234"), "최민준", new Address(23423, "역삼거리", "디테일", "기타"), "talk105@naver.com", "010-3244-6593");
        memberService.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        assertThat(member.getMemberId()).isEqualTo("talk105");
        assertThat(passwordEncoder.matches("password1234", findMember.getPassword())).isTrue();
    }

    @Test
    @Rollback(value = true)
    public void duplicateMember() throws Exception {
        //given
        Member member = new Member("talk105", passwordEncoder.encode("password1234"), "최민준", new Address(23423, "역삼거리", "디테일", "기타"), "talk105@naver.com", "010-3244-6593");
        memberService.save(member);

        Member member2 = new Member("talk105", passwordEncoder.encode("password1234"), "최민준", new Address(23423, "역삼거리", "디테일", "기타"), "talk105@naver.com", "010-3244-6593");

        //then
        // 중복 아이디 체크 - 에러발생 O
        assertThrows(ApiException.class, () -> {
            memberService.save(member2);
        });

    }

    @Test
    public void updateMember() throws Exception {
        //given
        Member member = new Member("talk105", "password3", "최민준", new Address(23423, "역삼거리", "디테일", "기타"), "talk105@naver.com", "010-3244-6593");
        memberService.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId()).get();
        findMember.updateMember("f", "sd", "-3432", null);


        //then
    }
}