package refrigerator.yeonho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import refrigerator.yeonho.constant.Role;
import refrigerator.yeonho.dto.MemberDto;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(memberDto.getLoginId());
        member.setNickName(memberDto.getNickName());
        member.setRole(Role.USER);
        String password =passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(password);

        return member;
    }


}
