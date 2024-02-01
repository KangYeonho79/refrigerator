package refrigerator.yeonho.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import refrigerator.yeonho.dto.RecipeDto;
import refrigerator.yeonho.dto.RecipeRatingDto;

@Entity
@Getter
@Setter
@Table(name="RecipeRating")
public class RecipeRating {
    @Id
    @Column(name = "RecipeRating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_id")
    private Long memberId;

    @Column
    private Long RecipeId;

    @Column
    private Integer rating;

    @Column
    private String loginId;

    @Column
    private String cookName;


}
