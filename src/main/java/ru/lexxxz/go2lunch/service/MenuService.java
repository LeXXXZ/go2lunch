package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.repository.jpa.DishRepository;
import ru.lexxxz.go2lunch.repository.jpa.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@Service("menuService")
@Transactional(readOnly = true)
public class MenuService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.dishRepository = dishRepository;
    }

    public List<Dish> getDayMenuDishes(LocalDate date, Integer restaurantId) {
        Integer dayMenuId = menuRepository.findMenuIdByRestaurantIdAndDate(restaurantId, date);
        log.info("Rest with id: {} menu for {}", restaurantId, date);
        return dishRepository.getAllByMenu_Id(dayMenuId);
    }

    public List<Dish> getTodayMenuDishes(Integer restaurantId) {
        return getDayMenuDishes(LocalDate.now(), restaurantId);
    }
}
