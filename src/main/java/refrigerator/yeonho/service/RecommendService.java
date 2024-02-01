package refrigerator.yeonho.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.entity.RecipeRating;
import refrigerator.yeonho.repository.RecipeRatingRepository;
import refrigerator.yeonho.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {
    private final RecipeRepository recipeRepository;
    private final RecipeRatingRepository recipeRatingRepository;
public List<RecipeEntity> recommendRecipe(String currentUsername) {

    List<RecipeRating> recipeRatings = recipeRatingRepository.findByloginId(currentUsername);

    // rating이 3 이상인 RecipeId 목록 추출
    List<Long> highRatingRecipeIds = recipeRatings.stream()
            .filter(recipeRating -> recipeRating.getRating() != null && recipeRating.getRating() >= 3)
            .map(RecipeRating::getRecipeId)
            .collect(Collectors.toList());

    log.info("Ids={}", highRatingRecipeIds);

    // highRatingRecipeIds에 해당하는 RecipeEntity 조회하여 main_ingredient 추출
    List<String> mainIngredients = recipeRepository.findByIdIn(highRatingRecipeIds)
            .stream()
            .map(RecipeEntity::getMainIngredient)
            .collect(Collectors.toList());

    log.info("재료는={}", mainIngredients);

    // mainIngredients와 일치하는 레시피 조회
    List<RecipeEntity> recommendRecipes = recipeRepository.findAllByMainIngredientIn(mainIngredients);

    log.info("객체사이즈는={}", recommendRecipes.size());
    return recommendRecipes;



}

}


