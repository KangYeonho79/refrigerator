package refrigerator.yeonho.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.entity.RecipeRating;

@Getter

@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecipeRatingDto {
    @Id
    private Long id;
    private Long RecipeId;
    private Integer rating;
    private String loginId;
    private String cookName;


    public static RecipeRatingDto toRecipeRatingDto(RecipeRating recipeRating) {


        RecipeRatingDto recipeRatingDto = new RecipeRatingDto();
        recipeRatingDto.setRecipeId(recipeRating.getRecipeId());
        recipeRatingDto.setRating(recipeRating.getRating());
        recipeRatingDto.setId(recipeRating.getId());
        recipeRatingDto.setLoginId(recipeRating.getLoginId());
        recipeRatingDto.setCookName(recipeRating.getCookName());

        return recipeRatingDto;
    }

}
