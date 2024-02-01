package refrigerator.yeonho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.yeonho.entity.Fridge;

import java.util.List;

public interface FridgeRepository extends JpaRepository<Fridge,Long> {
    List<Fridge> findByMemberId(Long memberId);

}
