package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update User as u set u.isMailing = true where u.email = ?1")
    void subscribeToNewsletter(String email);

    @Modifying
    @Query("update User as u set u.isMailing = false where u.email = ?1")
    void unsubscribeToNewsletter(String email);
}
