package refrigerator.yeonho.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import refrigerator.yeonho.entity.Fridge;
import refrigerator.yeonho.entity.RecipeEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class FridgeDto {

    private Long id;
    private String ingredient;
    private Long memberId;
    private LocalDateTime createdDate;
    private String formatDate;




}
