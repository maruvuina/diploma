package by.bsu.recipebook.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "tag")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Integer idTag;

    @NotBlank
    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tagSet")
    private Set<Recipe> recipes = new HashSet<>();
}
