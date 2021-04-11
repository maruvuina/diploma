package by.bsu.recipebook.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "refresh_token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_refresh_token")
    private Integer idRefreshToken;

    @NotBlank(message = "Please check token.")
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "created_date")
    private Instant createdDate;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RefreshToken{");
        sb.append("idRefreshToken=").append(idRefreshToken);
        sb.append(", token='").append(token).append('\'');
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
        RefreshToken that = (RefreshToken) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(createdDate, that.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(token)
                .append(createdDate)
                .toHashCode();
    }
}
