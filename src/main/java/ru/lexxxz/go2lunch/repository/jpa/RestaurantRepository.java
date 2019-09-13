package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>
{
    List<Restaurant> findAllByIdNotNullOrderByName();
}
