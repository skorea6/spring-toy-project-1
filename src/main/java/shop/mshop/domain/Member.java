package shop.mshop.domain;

import lombok.Getter;
import shop.mshop.util.RandomUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseDomain {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "id")
    private String memberId;

    private String password;
    private String name;

    @Embedded
    private Address address;

    private String email;
    private String phoneNumber;
    private String permission;
    private String sessionKey;


    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();


    public Member(String memberId, String password, String name, Address address, String email, String phoneNumber) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.permission = "user";
        this.sessionKey = RandomUtil.randomKey(35); // 임시로 초기에 발급하도록 10자리 랜덤키
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void updateMember(String name, String email, String phoneNumber, Address address) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Member() {
    }
}
