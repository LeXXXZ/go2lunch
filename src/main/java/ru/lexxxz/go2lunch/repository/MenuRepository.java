package ru.lexxxz.go2lunch.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurantIdOrderByDateDesc(Integer restaurantId);

    List<Menu> findAllByRestaurantIdAndDate(Integer restaurantId, LocalDate date);

    List<Menu> findAllByDate(LocalDate date);

    Optional<Menu> findByRestaurantIdAndId(int restId, int id);

    boolean existsByRestaurantIdAndDateAndName(int restId, LocalDate date, String name);

    boolean existsByIdAndRestaurantId(int id, int restId);
}