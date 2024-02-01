package refrigerator.yeonho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.entity.RecipeEntity;
import refrigerator.yeonho.entity.RecipeRating;

import java.util.List;

public interface RecipeRatingRepository extends JpaRepository<RecipeRating,Long> {

    List<RecipeRating> findByloginId(String loginId);

}
