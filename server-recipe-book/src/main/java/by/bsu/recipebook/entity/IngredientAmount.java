package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "ingredient_amount")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingredient_amount")
    private Integer idIngredientAmount;

    @NotBlank(message = "Please check ingredient measure amount.")
    @Column(name = "measure_amount")
    private String measureAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ingredient_fk", nullable = false)
    private Ingredient ingredient;

    @ManyToMany(mappedBy = "ingredientAmountSet")
    private Set<Recipe> recipes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientAmount that = (IngredientAmount) o;
        return idIngredientAmount != null &&
                idIngredientAmount.equals(that.idIngredientAmount);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(idIngredientAmount)
                .toHashCode();
    }
}
