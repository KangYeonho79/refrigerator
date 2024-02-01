package refrigerator.yeonho.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import refrigerator.yeonho.constant.Role;
import refrigerator.yeonho.dto.MemberDto;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService { //스프링 시큐리티 로그인 위해서

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member) {
        validateDuplicate(member);
        return memberRepository.save(member);
    }

    private void validateDuplicate(Member member) {
        Member findMember = memberRepository.findByloginId(member.getLoginId());
        if (findMember != null) {
            throw new IllegalStateException("이미 등록된 아이디입니다.");
        }
    }


    public void updateMember(MemberDto updateMember) {
        Member member = memberRepository.findByloginId(updateMember.getLoginId());
        member.setLoginId(updateMember.getLoginId());
        member.setRole(Role.USER);
        member.setNickName(updateMember.getNickName());
        String password =passwordEncoder.encode(updateMember.getPassword());
        member.setPassword(password);

        memberRepository.save(member);
    }

    public void deleteMember(String loginId) {
        Member member = memberRepository.findByloginId(loginId);
        if (member != null) {
            memberRepository.delete(member);
        }
    }





    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByloginId(loginId);

        if (member == null) {
            throw new UsernameNotFoundException("해당 사용자가 없습니다");
        }
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole().toString()) //객체니까 String으로 받기 위값
                .build();
    }


}
