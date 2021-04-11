package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "recipe_likes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recipe_likes")
    private Integer idRecipeLikes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recipe_fk")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_fk")
    private User user;

    @NotNull
    @Column(name = "like_type")
    private LikeType likeType;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecipeLike{");
        sb.append("idRecipeLikes=").append(idRecipeLikes);
        sb.append(", likeType=").append(likeType);
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
        RecipeLike that = (RecipeLike) o;
        return idRecipeLikes != null &&
                idRecipeLikes.equals(that.idRecipeLikes);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(idRecipeLikes)
                .toHashCode();
    }
}
