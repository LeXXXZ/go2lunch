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

    @Query("SELECT d FROM #{#entityName} d left JOIN Menu m ON d.menu.id= m.id WHERE m.id=?1 ORDER BY d.price")
    List<Dish> getAllByMenu_Id(Integer menuId);

    Optional<Dish> findDishByIdAndMenu_Id(int dishId, int menuId);

    List<Dish> findAllByMenu_IdOrderByPrice(int menuId);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} d WHERE d.id=:id")
    int delete(@Param("id") int dishId);

    boolean existsByNameAndMenuId(String dishName, int menuId);
}