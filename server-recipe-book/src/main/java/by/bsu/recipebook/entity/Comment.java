package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "comment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Integer idComment;

    @NotBlank(message = "Please enter comment content.")
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recipe_fk", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_fk", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent_comment_fk")
    private Comment parent;

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public void addChild(Comment comment) {
        this.children.add(comment);
    }

    public void removeChild(Comment comment) {
        this.children.remove(comment);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("idComment=").append(idComment);
        sb.append(", content='").append(content).append('\'');
        sb.append(", createdDate=").append(createdDate);
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
        Comment that = (Comment) o;
        return new EqualsBuilder()
                .append(content, that.content)
                .append(createdDate, that.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(content)
                .append(createdDate)
                .toHashCode();
    }
}
