package by.bsu.recipebook.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "followers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_fk")
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_fk")
    private User to;

    @Column(name = "is_subscribed")
    private boolean isSubscribed;

    public Followers(User from, User to) {
        this.from = from;
        this.to = to;
        this.isSubscribed = true;
    }
}
