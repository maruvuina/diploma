package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Followers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FollowersRepository extends JpaRepository<Followers, Integer>  {

    @Query("select f from Followers f where f.from.idUser=:id and f.isSubscribed=true")
    Page<Followers> findFollowings(@Param("id") Integer id, Pageable pageable);

    @Query("select f from Followers f where f.to.idUser=:id and f.isSubscribed=true")
    Page<Followers> findFollowers(@Param("id") Integer id, Pageable pageable);

    @Modifying
    @Query("update Followers f set f.isSubscribed=false where f.from.idUser=:id")
    void unsubscribe(@Param("id") Integer id);
}
