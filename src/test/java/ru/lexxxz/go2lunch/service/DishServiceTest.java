package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.lexxxz.go2lunch.RestaurantTestData.*;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    protected DishService dishService;

    @Test
    void getAllSorted() {
        List<Dish> all = dishService.getAll(100004);
        assertThat(all).usingElementComparatorIgnoringFields("menu").isEqualTo(DISHES_FOR_MENU_100004);
    }

    @Test
    void getById() {
        Dish dish = dishService.get(DISH_1_FOR_MENU_100004.getId(), 100004);
        assertThat(dish).isEqualToIgnoringGivenFields(DISH_1_FOR_MENU_100004, "menu");
    }

    @Test
    void getNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    dishService.get(1, 100004);
                });
    }

    @Test
    void getNotFoundAtMenu() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    dishService.get(DISH_1_FOR_MENU_100004.getId(), 1);
                });
    }

    @Test
    void delete() {
        dishService.delete(DISH_1_FOR_MENU_100004.getId(), 100004);
        assertThat(dishService.getAll(100004)).usingElementComparatorIgnoringFields("menu").isEqualTo(List.of(DISH_2_FOR_MENU_100004));
    }

    @Test
    void deleteNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    dishService.delete(1, 100004);
                });
    }

    @Test
    void update() {
        Dish updated = new Dish(DISH_1_FOR_MENU_100004);
        updated.setName("UpdatedDishName");
        dishService.update(new Dish(updated), 100004, DISH_1_FOR_MENU_100004.getId());
        assertThat(dishService.get(DISH_1_FOR_MENU_100004.getId(), 100004)).isEqualToIgnoringGivenFields(updated, "menu");

    }

    @Test
    void create() {
        Dish newDish = new Dish("Pasta", 2200);
        Dish createdRest = dishService.create(new Dish(newDish), 100004);
        newDish.setId(createdRest.getId());
        assertThat(createdRest).isEqualToIgnoringGivenFields(newDish, "menu");
    }

    @Test
    void createDuplicatedName() {
        Dish newRest = new Dish("CheeseBurger", 1001);
        assertThatExceptionOfType(IllegalRequestDataException.class)
                .isThrownBy(() -> {
                    dishService.create(new Dish(newRest), 100004);
                });
    }
}