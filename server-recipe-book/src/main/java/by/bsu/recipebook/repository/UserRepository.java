package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email=:email and u.active=true")
    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update User as u set u.isMailing = true where u.email = ?1")
    void subscribeToNewsletter(String email);

    @Modifying
    @Query("update User as u set u.isMailing = false where u.email = ?1")
    void unsubscribeToNewsletter(String email);

    @Nonnull
    @Override
    @Query("select u from User as u " +
            "where u.active=true and u.idUser=:id")
    Optional<User> findById(@Param("id") @Nonnull Integer id);

    @Query("select u.isMailing from User u where u.idUser=:id")
    Boolean isMailing(@Param("id") Integer id);
}
