package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "recipe")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recipe")
    private Integer idRecipe;

    @NotBlank(message = "Please check recipe name.")
    @Column(name = "recipe_name")
    private String recipeName;

    @Min(1)
    @Column(name = "cooking_time")
    private int cookingTime;

    @Min(1)
    @Column(name = "yield")
    private int yield;

    @NotBlank(message = "Please check recipe picture.")
    @Column(name = "main_picture_location")
    private String mainPicture;

    @Column(name = "is_delete")
    private boolean isDelete;

    @NotNull
    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "like_count")
    private int likeCount = 0;

    @NotNull
    @Column(name = "announce")
    private String announce;

    @OneToMany(mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setRecipe(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setRecipe(null);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_fk", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category_fk", nullable = false)
    private Category category;

    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "id_recipe_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_ingredient_amount_fk")
    )
    private Set<IngredientAmount> ingredientAmountSet = new HashSet<>();

    public void addIngredientAmount(IngredientAmount ingredientAmount) {
        this.ingredientAmountSet.add(ingredientAmount);
        ingredientAmount.getRecipes().add(this);
    }

    public void removeIngredientAmount(IngredientAmount ingredientAmount) {
        this.ingredientAmountSet.remove(ingredientAmount);
        ingredientAmount.getRecipes().remove(this);
    }

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instruction> instructions = new ArrayList<>();

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
        instruction.setRecipe(this);
    }

    public void removeInstruction(Instruction instruction) {
        this.instructions.remove(instruction);
        instruction.setRecipe(null);
    }

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeLike> recipeLikeList = new ArrayList<>();

    public void addRecipeLikes(RecipeLike recipeLike) {
        this.recipeLikeList.add(recipeLike);
        recipeLike.setRecipe(this);
    }

    public void removeRecipeLikes(RecipeLike recipeLike) {
        this.recipeLikeList.remove(recipeLike);
        recipeLike.setRecipe(null);
    }

    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "recipe_tag",
            joinColumns = @JoinColumn(name = "id_recipe_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_tag_fk")
    )
    private Set<Tag> tagSet = new HashSet<>();

    public void addTag(Tag tag) {
        this.tagSet.add(tag);
        tag.getRecipes().add(this);
    }

    public void removeTag(Tag tag) {
        this.tagSet.remove(tag);
        tag.getRecipes().remove(this);
    }

    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "recipe_cuisine",
            joinColumns = @JoinColumn(name = "id_recipe_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_cuisine_fk")
    )
    private Set<Cuisine> cuisineSet = new HashSet<>();

    public void addCuisine(Cuisine cuisine) {
        this.cuisineSet.add(cuisine);
        cuisine.getRecipes().add(this);
    }

    public void removeCuisine(Cuisine cuisine) {
        this.cuisineSet.remove(cuisine);
        cuisine.getRecipes().remove(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe{");
        sb.append("idRecipe=").append(idRecipe);
        sb.append(", recipeName='").append(recipeName).append('\'');
        sb.append(", cookingTime=").append(cookingTime);
        sb.append(", yield=").append(yield);
        sb.append(", mainPicture='").append(mainPicture).append('\'');
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", author=").append(author);
        sb.append(", category=").append(category);
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
        Recipe that = (Recipe) o;
        return new EqualsBuilder()
                .append(recipeName, that.recipeName)
                .append(cookingTime, that.cookingTime)
                .append(yield, that.yield)
                .append(mainPicture, that.mainPicture)
                .append(createdDate, that.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(recipeName)
                .append(cookingTime)
                .append(yield)
                .append(mainPicture)
                .append(createdDate)
                .toHashCode();
    }
}
