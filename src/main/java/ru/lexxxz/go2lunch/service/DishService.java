package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.repository.jpa.DishRepository;
import ru.lexxxz.go2lunch.repository.jpa.RestaurantRepository;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
@Transactional(readOnly = true)
public class DishService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restRepository;
    private final DishRepository dishRepository;

    @Autowired
    public DishService(RestaurantRepository restRepository, DishRepository dishRepository) {
        this.restRepository = restRepository;
        this.dishRepository = dishRepository;
    }

    public Dish get(int dishId, int restId) {
        checkNotFoundRest(restId);
        return dishRepository.findDishById(dishId).orElseThrow(() -> new NotFoundException("No such dish"));
    }

    @Transactional
    public void delete(int dishId, int restId) throws NotFoundException {
        log.info("Delete dish with id: {} from restaurant with id: {}", dishId, restId);
        checkNotFoundRest(restId);
        checkNotFoundWithId(dishRepository.delete(dishId) != 0, dishId);
    }

    @Transactional
    public Dish create(Dish dish, int restId) {
        assertNotNullEntity(dish);
        if (dishRepository.existsByNameAndRestaurantId(dish.getName(), restId)) {
            throw new IllegalRequestDataException("Dish " + dish.getName() + " already exists");
        }
        dish.setRestaurant(restRepository.getOne(restId));
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(Dish dish, int restId, int dishId) {
        assertNotNullEntity(dish);
        if (dishRepository.findDishById(dishId).orElse(null) != null) {
            dish.setRestaurant(restRepository.getOne(restId));
            dishRepository.save(dish);
        } else throw new IllegalRequestDataException("No dish with id: " + dish + " for restaurant with id: " + restId);
    }

    private void checkNotFoundRest(int restId) {
        checkNotFoundWithId(restRepository.existsById(restId), restId);
    }

    public List<Dish> getAll(int restId) {
        checkNotFoundRest(restId);
        return dishRepository.findAllByRestaurant_IdOrderByPrice(restId);
    }

}
