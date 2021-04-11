package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "verification_token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    private Integer idVerificationToken;

    @NotBlank(message = "Please check token.")
    @Column(name = "token")
    private String token;

    @Column(name = "expity_date")
    private Instant expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_verification_token")
    private User user;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VerificationToken{");
        sb.append("idVerificationToken=").append(idVerificationToken);
        sb.append(", token='").append(token).append('\'');
        sb.append(", expiryDate=").append(expiryDate);
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
        VerificationToken that = (VerificationToken) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(expiryDate, that.expiryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(token)
                .append(expiryDate)
                .toHashCode();
    }
}
