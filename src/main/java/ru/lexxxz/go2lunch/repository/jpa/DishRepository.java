package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.to.DishTo;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    Optional<Dish> findDishById(int dishId);

//    @Query("SELECT m.dishes FROM Menu m WHERE m.id=:id")
    List<Dish> findAllByMenus_Id(@Param("id") int id);

    @Query("SELECT NEW ru.lexxxz.go2lunch.to.DishTo(d.id, d.name, d.price) " +
            "FROM Dish d join d.restaurant r where r.id = :id ORDER BY d.name")
    List<DishTo> findAllByRestaurant_IdOrderByName(@Param("id") int restId);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} d WHERE d.id=:id")
    int delete(@Param("id") int dishId);

    boolean existsByNameAndRestaurantId(String dishName, int restaurantId);

    Optional<Dish> findByRestaurant_Id(int restId);
}