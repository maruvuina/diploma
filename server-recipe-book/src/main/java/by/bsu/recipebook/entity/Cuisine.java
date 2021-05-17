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
@Table(name = "cuisine")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuisine")
    private Integer idCuisine;

    @NotBlank(message = "Please check cuisine name.")
    @Column(name = "cuisine_name")
    private String cuisineName;

    @ManyToMany(mappedBy = "cuisineSet")
    private Set<Recipe> recipes = new HashSet<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cuisine{");
        sb.append("idCuisine=").append(idCuisine);
        sb.append(", cuisineName='").append(cuisineName).append('\'');
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
        Cuisine that = (Cuisine) o;
        return new EqualsBuilder()
                .append(cuisineName, that.cuisineName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(cuisineName)
                .toHashCode();
    }
}
