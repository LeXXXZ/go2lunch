package ru.lexxxz.go2lunch.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.to.RestaurantTo;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>
{
    List<Restaurant> findAll(Sort sort);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT distinct NEW ru.lexxxz.go2lunch.to.RestaurantTo(r.id, r.name) "
        + "FROM Restaurant r join Menu m on r.id = m.restaurant.id where m.date=:date ")
    List<RestaurantTo> getAllWithTodayMenu(@Param("date") LocalDate date);
}
