package refrigerator.yeonho;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.repository.RecipeRepository;

@SpringBootTest
public class RecipeEntityTest {

    @Autowired
    RecipeRepository recipeRepository;

//    @Test
    public void createRecipeTest() {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setTakingTime("4분");
        recipeEntity.setImage("이미지입니다.");
        recipeEntity.setHowtocook("5분동안 끓이고 다지고 볶고");
        recipeEntity.setIngredients("당근,양파,참치");
        recipeEntity.setServing("3인분");
        recipeEntity.setMain_ingredient("참치");

        recipeRepository.save(recipeEntity);


    }


}
