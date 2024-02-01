package refrigerator.yeonho.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import refrigerator.yeonho.dto.MemberDto;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.repository.MemberRepository;
import refrigerator.yeonho.service.MemberService;
import refrigerator.yeonho.service.RecipeRatingService;


import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RecipeRatingService recipeService;


    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        //별점 기능(요리 264개)
        List<RecipeEntity> recipeRating = recipeService.recipeRating();
        model.addAttribute("recipeRating", recipeRating);

        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto,
                         BindingResult bindingResult, Model model,
                         @RequestParam("rating") List<Integer> ratings,@RequestParam("id") List<Long> id) {

        bindingError(memberDto,bindingResult);
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }
        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signup";
        }


        recipeService.recipeRatingSave(memberDto.getLoginId(), id,ratings);


        return "redirect:/member/login";
    }


    @GetMapping("/login")
    public String login() {
        return "member/login";
    }


    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 패스워드가 잘못 되었습니다");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/";
    }


    @GetMapping("/modify")
    public String modify(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        model.addAttribute("currentUserName", currentUsername());

        return "member/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid MemberDto memberDto,
                         BindingResult bindingResult, Model model,HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!memberDto.getPassword().equals(memberDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("memberDto", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUserName", currentUsername());
            return "member/modify";
        }

        memberService.updateMember(memberDto);
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/";

    }

    @GetMapping("/withdraw")
    public String withdraw(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        memberService.deleteMember(currentUsername);

        //로그아웃
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/";
    }



    private static void bindingError(MemberDto memberDto, BindingResult bindingResult) {
        if (!memberDto.getPassword().equals(memberDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("memberDto", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (Pattern.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*", memberDto.getLoginId())) {
            bindingResult.addError(new FieldError("memberDto", "loginId", "아이디에 한글이 포함돼 있으면 안 됩니다."));
        }


    }

    private static String currentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return currentUsername;
    }
}

