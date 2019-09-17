package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.repository.jpa.DishRepository;
import ru.lexxxz.go2lunch.repository.jpa.MenuRepository;
import ru.lexxxz.go2lunch.repository.jpa.RestaurantRepository;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

@Service("menuService")
@Transactional(readOnly = true)
public class MenuService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.dishRepository = dishRepository;
    }

    public List<Dish> getDayMenuDishes(LocalDate date, int restaurantId) {
        checkNotFoundRestaurant(restaurantId);
        Integer dayMenuId = menuRepository.findMenuIdByRestaurantIdAndDate(restaurantId, date);
        log.info("Dishes for the menu @{} for the restaurant with id: {} ", date, restaurantId);
        return dishRepository.getAllByRestaurant_Id(restaurantId);
    }

    public List<Dish> getTodayMenuDishes(int restaurantId) {
        return getDayMenuDishes(LocalDate.now(), restaurantId);
    }

    public List<Menu> getAll(int restaurantId) {
        checkNotFoundRestaurant(restaurantId);
        return menuRepository.findAllByRestaurantIdOrderByDateDesc(restaurantId);
    }

    public Menu get(LocalDate date, int restaurantId) {
        return menuRepository.findByRestaurantIdAndDate(restaurantId, date).orElseThrow(() -> new NotFoundException("No such menu"));
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        assertNotNullEntity(menu);
        if (menu.getDate() == null) {
            menu.setDate(LocalDate.now());
        }

        if (menuRepository.findByRestaurantIdAndDate(restaurantId , menu.getDate()).orElse(null) == null) {
            menu.setRestaurant(restaurantRepository.getOne(restaurantId));
            return menuRepository.save(menu);
        }
        else throw new IllegalRequestDataException("Menu already exists");
    }

    @Transactional
    public void update(Menu menu, int restaurantId, LocalDate date) {
        assertNotNullEntity(menu);
        if (menuRepository.findByRestaurantIdAndDate(restaurantId , date).orElse(null)  != null){
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        menuRepository.save(menu);}
        else throw new IllegalRequestDataException("No menu for restaurant with id: " + restaurantId + " @" + date.toString());
    }

    private void checkNotFoundRestaurant(int restaurantId) {
        checkNotFoundWithId(restaurantRepository.existsById(restaurantId), restaurantId);
    }
}
