package refrigerator.yeonho.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.entity.RecipeRating;
import refrigerator.yeonho.repository.MemberRepository;
import refrigerator.yeonho.repository.RecipeRatingRepository;
import refrigerator.yeonho.repository.RecipeRepository;

import javax.sql.DataSource;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeRatingService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final RecipeRatingRepository recipeRatingRepository;


    public List<RecipeEntity> recipeRating() {
        List<RecipeEntity> randomList = new ArrayList<>();

        //난수 5개 생성
        Random random = new Random();
        int[] randomNumbers = new int[5]; // 크기가 5인 배열 생성

        for (int i = 0; i < 5; i++) {
            randomNumbers[i] = random.nextInt(264) + 1; // 배열에 난수 저장
        }

        for (int i = 0; i < 5; i++) {
            Optional<RecipeEntity> randomRecipe = recipeRepository.findById((long)randomNumbers[i]);
            randomList.add(randomRecipe.get());
        }
        log.info("List={}", randomList);

        return randomList;
    }

    @Transactional
    public void recipeRatingSave(String loginId,List<Long> id,List<Integer> ratings) {
        for(int i=0;i<5;i++){
        Member findMember = memberRepository.findByloginId(loginId);
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id.get(i));

        RecipeRating recipeRating = new RecipeRating();
        recipeRating.setMemberId(findMember.getId());
        recipeRating.setRecipeId(id.get(i));
        recipeRating.setRating(ratings.get(4-i));
        recipeRating.setLoginId(findMember.getLoginId());
        recipeRating.setCookName(recipeEntity.get().getCookName());

        recipeRatingRepository.save(recipeRating);
    }
}

    @Transactional
    public void recipeRatingStringSave(String loginId,Long id,Integer rating) {

            Member findMember = memberRepository.findByloginId(loginId);
            Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);

            RecipeRating recipeRating = new RecipeRating();
            recipeRating.setMemberId(findMember.getId());
            recipeRating.setRecipeId(id);
            recipeRating.setRating(rating);
            recipeRating.setLoginId(findMember.getLoginId());
            recipeRating.setCookName(recipeEntity.get().getCookName());


            recipeRatingRepository.save(recipeRating);

    }


}




