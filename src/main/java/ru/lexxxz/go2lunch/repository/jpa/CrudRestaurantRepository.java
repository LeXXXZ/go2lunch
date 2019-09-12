package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lexxxz.go2lunch.model.Restaurant;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

}