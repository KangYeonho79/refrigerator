package refrigerator.yeonho.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import refrigerator.yeonho.dto.RecipeDto;

@Entity
@Getter
@Setter
@Table(name = "recipe")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_id")
    private Long id;

    @Column
    private String cookName;
    @Column
    private String mainIngredient; // 메인재료

    @Column
    private String serving; //몇인분

    @Column
    private String takingTime;  //소요시간

    @Column(columnDefinition = "LONGTEXT")
    private String ingredients;  //모든재료

    @Column(columnDefinition = "LONGTEXT")
    private String howtocook;  //요리 방법

    @Column
    private String image; //요리 이미지

    public static RecipeEntity toRecipeEntity(RecipeDto recipeDto) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipeDto.getId());
        recipeEntity.setCookName(recipeDto.getCookName());
        recipeEntity.setMainIngredient(recipeDto.getMainIngredient());
        recipeEntity.setServing(recipeDto.getServing());
        recipeEntity.setTakingTime(recipeDto.getTakingTime());
        recipeEntity.setIngredients(recipeDto.getIngredients());
        recipeEntity.setHowtocook(recipeDto.getHowtocook());
        recipeEntity.setImage(recipeDto.getImage());

        return recipeEntity;
    }
}

