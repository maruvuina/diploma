package by.bsu.recipebook.repository;

import by.bsu.recipebook.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepository extends JpaRepository<Cuisine, Integer> {
    Cuisine findByCuisineName(String cuisineName);
}
