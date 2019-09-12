package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

@Query("SELECT m FROM #{#entityName} m WHERE m.restaurant.id = ?1 and m.date = ?2 ")
    Menu getByRestaurantIdAndDate(Integer restaurantId, LocalDate date);

@Query("SELECT m.id FROM #{#entityName} m WHERE m.restaurant.id = ?1 and m.date = ?2 ")
    Integer findMenuIdByRestaurantIdAndDate(Integer restaurantId, LocalDate date);

    List<Menu> findAllByRestaurantId(Integer restaurantId);
}