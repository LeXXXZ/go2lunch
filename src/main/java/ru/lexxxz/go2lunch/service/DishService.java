package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.repository.jpa.DishRepository;
import ru.lexxxz.go2lunch.repository.jpa.MenuRepository;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
@Transactional(readOnly = true)
public class DishService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @Autowired
    public DishService(MenuRepository menuRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.dishRepository = dishRepository;
    }

    public Dish get(int dishId, int menuId) {
        return dishRepository.findDishByIdAndMenu_Id(dishId, menuId).orElseThrow(() -> new NotFoundException("No such dish"));
    }

    @Transactional
    public void delete(int dishId, int menuId) throws NotFoundException {
        log.info("Delete dish with id: {} from menu with id: {}", dishId, menuId);
        checkNotFoundMenu(menuId);
        checkNotFoundWithId(dishRepository.delete(dishId) != 0, dishId);
    }

    @Transactional
    public Dish create(Dish dish, int menuId) {
        assertNotNullEntity(dish);
        if (dishRepository.existsByNameAndMenuId(dish.getName(), menuId)) {
            throw new IllegalRequestDataException("Dish " + dish.getName() + " already exists");
        }
        //TODO FIX
        dish.setMenu(menuRepository.getOne(menuId));
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(Dish dish, int menuId, int dishId) {
        assertNotNullEntity(dish);
        if (dishRepository.findDishByIdAndMenu_Id(dish.getId(), menuId).orElse(null) != null) {
            //TODO FIX
            dish.setMenu(menuRepository.getOne(menuId));
            dishRepository.save(dish);
        } else throw new IllegalRequestDataException("No dish with id: " + dish + " for menu with id: " + menuId);
    }

    private void checkNotFoundMenu(int menuId) {
        checkNotFoundWithId(menuRepository.existsById(menuId), menuId);
    }

    public List<Dish> getAll(int menuId) {
        checkNotFoundMenu(menuId);
        return dishRepository.findAllByMenu_IdOrderByPrice(menuId);
    }

}
