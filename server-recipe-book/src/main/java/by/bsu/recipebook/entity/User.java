package by.bsu.recipebook.entity;

import by.bsu.recipebook.validator.ParamRegex;
import by.bsu.recipebook.validator.ValidPassword;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;
import javax.validation.constraints.*;

@Setter
@Getter
@Entity
@Table(name = "user")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Email(regexp = ParamRegex.EMAIL_REGEX, message = "Email should be valid.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ValidPassword
    //@Min(value = 8, message = "Password should not be less than 8 characters.")
    //@Max(value = 20, message = "Password should not be greater than 20 characters.")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Please check your full name.")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "avatar_location")
    private String avatarLocation;

    @Column(name = "authorise")
    private boolean authorise;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @ManyToMany (fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user_fk"),
            inverseJoinColumns = @JoinColumn(name = "id_role_fk")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
        recipe.setAuthor(this);
    }

    public void removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
        recipe.setAuthor(null);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeLike> recipeLikeList = new ArrayList<>();

    public void add(RecipeLike recipeLike) {
        this.recipeLikeList.add(recipeLike);
        recipeLike.setUser(this);
    }

    public void remove(RecipeLike recipeLike) {
        this.recipeLikeList.remove(recipeLike);
        recipeLike.setUser(null);
    }

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private VerificationToken verificationToken;

    public void setVerificationToken(VerificationToken verificationToken) {
        if (verificationToken == null) {
            if (this.verificationToken != null) {
                this.verificationToken.setUser(null);
            }
        } else {
            verificationToken.setUser(this);
        }
        this.verificationToken = verificationToken;
    }

    @OneToMany(mappedBy = "to")
    private List<Followers> followers = new ArrayList<>();

    public void addFollowers(Followers followers) {
        this.followers.add(followers);
        followers.setTo(this);
    }

    public void removeFollowers(Followers followers) {
        this.followers.remove(followers);
        followers.setTo(null);
    }

    @OneToMany(mappedBy = "from")
    private List<Followers> following = new ArrayList<>();

    public void addFollowing(Followers followers) {
        this.following.add(followers);
        followers.setFrom(this);
    }

    public void removeFollowing(Followers followers) {
        this.following.remove(followers);
        followers.setFrom(null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("idUser=").append(idUser);
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append(", avatarLocation='").append(avatarLocation).append('\'');
        sb.append(", authorise=").append(authorise);
        sb.append(", active=").append(active);
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
        User that = (User) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(email)
                .toHashCode();
    }
}
