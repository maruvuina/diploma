package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "instruction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instruction")
    private Integer idInstruction;

    @NotNull
    @Column(name = "step_number")
    private Integer stepNumber;

    @NotBlank(message = "Please check recipe instruction description.")
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recipe_fk", nullable = false)
    private Recipe recipe;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Instruction{");
        sb.append("idInstruction=").append(idInstruction);
        sb.append(", stepNumber=").append(stepNumber);
        sb.append(", description='").append(description).append('\'');
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
        Instruction that = (Instruction) o;
        return idInstruction != null &&
                idInstruction.equals(that.idInstruction);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(idInstruction)
                .toHashCode();
    }
}
