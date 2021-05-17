package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Followers;
import by.bsu.recipebook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface FollowersRepository extends JpaRepository<Followers, Integer>  {
    @Query("select f from Followers f where f.from.idUser=:id and f.isSubscribed=true")
    Page<Followers> findFollowings(@Param("id") Integer id, Pageable pageable);

    @Query("select f from Followers f where f.to.idUser=:id and f.isSubscribed=true")
    Page<Followers> findFollowers(@Param("id") Integer id, Pageable pageable);

    @Query("select f from Followers f " +
            "where f.from.idUser=:idFrom and f.to.idUser=:idTo and f.isSubscribed=true")
    Followers isSubscribed(@Param("idFrom") Integer idFrom, @Param("idTo") Integer idTo);

    Optional<Followers> findByFromAndTo(User from, User to);
}
