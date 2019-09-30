package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.repository.DishRepository;
import ru.lexxxz.go2lunch.repository.RestaurantRepository;
import ru.lexxxz.go2lunch.to.DishTo;
import ru.lexxxz.go2lunch.util.DishUtil;
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
    public Dish create(DishTo dishTo, int restId) {
        assertNotNullEntity(dishTo);
        if (dishRepository.existsByNameAndRestaurantId(dishTo.getName(), restId)) {
            throw new IllegalRequestDataException("Dish " + dishTo.getName() + " already exists");
        }
        Dish created = DishUtil.createNewFromTo(dishTo);
        created.setRestaurant(restRepository.getOne(restId));
        return dishRepository.save(created);
    }

    @Transactional
    public void update(DishTo dishTo, int restId, int dishId) {
        assertNotNullEntity(dishTo);
        Dish dish = get(dishId, restId);
        checkNotFoundRest(restId);
        checkNotFoundWithId(dishRepository.save(DishUtil.updateFromTo(dish, dishTo)), dish.getId());
    }

    private void checkNotFoundRest(int restId) {
        checkNotFoundWithId(restRepository.existsById(restId), restId);
    }

    public List<DishTo> getAll(int restId) {
        checkNotFoundRest(restId);
        return dishRepository.findAllByRestaurant_IdOrderByName(restId);
    }

    public DishTo getTo(int dishId, int restId) {
        return DishUtil.asTo(get(dishId,restId));
    }
}
