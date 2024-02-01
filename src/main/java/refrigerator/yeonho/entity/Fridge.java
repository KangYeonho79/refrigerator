package refrigerator.yeonho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import refrigerator.yeonho.dto.FridgeDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="fridge")
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    private Long id;

    private String ingredient;

    private Long memberId;

    @CreatedDate
    private LocalDateTime createdDate;
    private String formatDate;

    public static FridgeDto tofridgeDto(Fridge fridge) {
        FridgeDto fridgeDto = new FridgeDto();
        fridgeDto.setId(fridge.getId());
        fridgeDto.setIngredient(fridge.getIngredient());
        fridgeDto.setCreatedDate(fridge.getCreatedDate());
        fridgeDto.setMemberId(fridge.getMemberId());
        return fridgeDto;
    }




}
