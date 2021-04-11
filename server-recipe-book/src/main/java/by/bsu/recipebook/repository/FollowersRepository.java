package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Followers;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowersRepository extends JpaRepository<Followers, Integer>  {

    @Query("select f from Followers f where f.from.idUser=:id and f.isSubscribed=true")
    List<Followers> findFollowings(@Param("id") Integer id);

    @Query("select f from Followers f where f.to.idUser=:id and f.isSubscribed=true")
    List<Followers> findFollowers(@Param("id") Integer id);

    @Modifying
    @Query("update Followers f set f.isSubscribed=false where f.from.idUser=:id")
    void unsubscribe(@NotNull Integer id);

}
