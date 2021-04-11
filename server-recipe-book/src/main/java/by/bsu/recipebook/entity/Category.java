package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Integer idCategory;

    @NotBlank(message = "Please check category name.")
    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
        recipe.setCategory(this);
    }

    public void removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
        recipe.setCategory(null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("idCategory=").append(idCategory);
        sb.append(", categoryName='").append(categoryName).append('\'');
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
        Category that = (Category) o;
        return new EqualsBuilder()
                .append(categoryName, that.categoryName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(categoryName)
                .toHashCode();
    }
}
