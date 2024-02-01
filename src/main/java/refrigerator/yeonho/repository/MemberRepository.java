package refrigerator.yeonho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.yeonho.entity.Member;
import refrigerator.yeonho.entity.RecipeEntity;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByloginId(String loginId);

}
