package refrigerator.yeonho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.entity.RecipeRating;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity,Long> {


    List<RecipeEntity> findByIdIn(List<Long> ids);

    List<RecipeEntity> findAllByMainIngredientIn(List<String> mainIngredients);

    List<RecipeEntity> findByMainIngredient(String mainIngredient);
}
