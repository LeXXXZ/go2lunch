package ru.lexxxz.go2lunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.to.MenuTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurantIdOrderByDateDesc(Integer restaurantId);

    List<Menu> findAllByRestaurantIdAndDate(Integer restaurantId, LocalDate date);

    Optional<Menu> findByRestaurantIdAndDate(Integer restId, LocalDate date);

    @Query("SELECT NEW ru.lexxxz.go2lunch.to.MenuTo(r.id, r.name, r.votes.size, m.id) " +
            "FROM Menu m join m.restaurant r where m.date = :date")
    List<MenuTo> getDayMenus(@Param("date") LocalDate date);

    boolean existsByRestaurantIdAndDateAndName(int restId, LocalDate date, String name);

    boolean existsByIdAndRestaurantId(int id, int restId);
}