package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> getAllByRestaurant_Id(int menuId);

    Optional<Dish> findDishById(int dishId);

    List<Dish> findAllByRestaurant_IdOrderByPrice(int restId);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} d WHERE d.id=:id")
    int delete(@Param("id") int dishId);

    boolean existsByNameAndRestaurantId(String dishName, int restaurantId);
}