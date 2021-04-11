package by.bsu.recipebook.entity.embeddable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecipeIngredientKey implements Serializable {

    @Column(name = "recipe_id")
    private Integer recipeId;

    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecipeIngredientKey{");
        sb.append("recipeId=").append(recipeId);
        sb.append(", ingredientid=").append(ingredientId);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientKey that = (RecipeIngredientKey) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }
}
