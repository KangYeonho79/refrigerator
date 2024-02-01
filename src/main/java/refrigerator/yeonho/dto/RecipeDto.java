package refrigerator.yeonho.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import refrigerator.yeonho.entity.RecipeEntity;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private Long id;

    private String cookName;

    private String mainIngredient; // 메인재료

    private String serving; //몇인분

    private String takingTime;  //소요시간

    private String ingredients;  //모든재료

    private String howtocook;  //요리 방법

    private String image; //요리 이미지

    //entitiy->dto
    public static RecipeDto toRecipeDto(RecipeEntity recipeEntity) {

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipeEntity.getId());
        recipeDto.setCookName(recipeEntity.getCookName());
        recipeDto.setMainIngredient(recipeEntity.getMainIngredient());
        recipeDto.setServing(recipeEntity.getServing());
        recipeDto.setTakingTime(recipeEntity.getTakingTime());
        recipeDto.setIngredients(recipeEntity.getIngredients());
        recipeDto.setHowtocook(recipeEntity.getHowtocook());
        recipeDto.setImage(recipeEntity.getImage());
        return recipeDto;
    }




}
