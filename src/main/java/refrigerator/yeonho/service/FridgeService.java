package refrigerator.yeonho.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import refrigerator.yeonho.dto.FridgeDto;
import refrigerator.yeonho.entity.Fridge;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.repository.FridgeRepository;
import refrigerator.yeonho.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class FridgeService {
    private final FridgeRepository fridgeRepository;
    private final MemberRepository memberRepository;

    public void save(String loginId, String ingredient) {
        Member member = memberRepository.findByloginId(loginId);
        Fridge fridge = new Fridge();
        fridge.setIngredient(ingredient);
        fridge.setMemberId(member.getId());
        fridge.setCreatedDate(LocalDateTime.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. d, yyyy", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedDate = formatter.format(localDateTime);
        System.out.println(formattedDate);

        fridge.setFormatDate(formattedDate);

        fridgeRepository.save(fridge);
    }

    public List<Fridge> findFridge(String loginId) {
        Member member = memberRepository.findByloginId(loginId);
        List<Fridge> fridges = fridgeRepository.findByMemberId(member.getId());
        return fridges;
    }

}
