package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Modifying
    @Query("update User as u set u.email = ?1, u.fullName = ?2 where u.idUser = ?3")
    boolean updateUserById(String email, String fullName, Integer userId);

    boolean existsByEmail(String email);

}
