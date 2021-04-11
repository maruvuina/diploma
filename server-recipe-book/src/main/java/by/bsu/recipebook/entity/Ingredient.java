package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "ingredient")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingredient")
    private Integer idIngredient;

    @NotBlank(message = "Please check ingredient name.")
    @Column(name = "ingredient_name")
    private String ingredientName;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IngredientAmount> ingredientAmountSet = new HashSet<>();

    public void addIngredientAmount(IngredientAmount ingredientAmount) {
        this.ingredientAmountSet.add(ingredientAmount);
        ingredientAmount.setIngredient(this);
    }

    public void removeIngredientAmount(IngredientAmount ingredientAmount) {
        this.ingredientAmountSet.remove(ingredientAmount);
        ingredientAmount.setIngredient(null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ingredient{");
        sb.append("idIngredient=").append(idIngredient);
        sb.append(", ingredientName='").append(ingredientName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return new EqualsBuilder()
                .append(ingredientName, that.ingredientName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(ingredientName)
                .toHashCode();
    }
}
