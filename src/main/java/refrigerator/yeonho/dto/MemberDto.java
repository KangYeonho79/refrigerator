package refrigerator.yeonho.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import refrigerator.yeonho.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotBlank(message="로그인아이디는 필수 항목입니다.")
    private String loginId;

    @NotBlank(message="비밀번호는 필수 항목입니다.")
    @Length(min=6,max=12,message = "비밀번호는 최소6자 최대12자 입니다.")
    private String password;

    @NotBlank(message = "비밀번호확인은 필수 항목입니다.")
    private String passwordCheck;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    private String nickName;


}
