package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Dish;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM #{#entityName} d left JOIN Menu m ON d.menu.id= m.id WHERE m.id=?1 ORDER BY d.price")
    List<Dish> getAllByMenu_Id(Integer menuId);

}