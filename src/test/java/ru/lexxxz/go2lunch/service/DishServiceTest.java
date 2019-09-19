package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.to.DishTo;
import ru.lexxxz.go2lunch.util.DishUtil;
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
        List<DishTo> all = dishService.getAll(100010);
        assertThat(all).usingElementComparatorIgnoringFields("menu", "restaurant")
                .isEqualTo(List.of(
                DishUtil.asTo(DISH_1_FOR_MENU_100021),
                DishUtil.asTo(DISH_4_FOR_MENU_100023),
                DishUtil.asTo(DISH_2_FOR_MENU_100021),
                DishUtil.asTo(DISH_3_FOR_MENU_100023)
        ));
    }

    @Test
    void getById() {
        Dish dish = dishService.get(DISH_1_FOR_MENU_100021.getId(), 100010);
        assertThat(dish).isEqualToIgnoringGivenFields(DISH_1_FOR_MENU_100021, "menus", "restaurant");
    }

    @Test
    void getNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    dishService.get(1, 100010);
                });
    }

    @Test
    void getNotFoundAtMenu() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    dishService.get(DISH_1_FOR_MENU_100021.getId(), 1);
                });
    }

    @Test
    void delete() {
        dishService.delete(DISH_1_FOR_MENU_100021.getId(), 100010);
        assertThat(dishService.getAll(100010)).usingElementComparatorIgnoringFields("menu", "restaurant")
                .isEqualTo(List.of(
                        DishUtil.asTo(DISH_4_FOR_MENU_100023),
                        DishUtil.asTo(DISH_2_FOR_MENU_100021),
                        DishUtil.asTo(DISH_3_FOR_MENU_100023)));
    }

    @Test
    void deleteNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    dishService.delete(1, 100010);
                });
    }

    @Test
    void update() {
        Dish updated = new Dish(DISH_1_FOR_MENU_100021);
        updated.setName("UpdatedDishName");
        dishService.update(DishUtil.asTo(new Dish(updated)), 100010, DISH_1_FOR_MENU_100021.getId());
        assertThat(dishService.get(DISH_1_FOR_MENU_100021.getId(), 100010))
                .isEqualToIgnoringGivenFields(updated, "menus", "restaurant");

    }

    @Test
    void create() {
        Dish newDish = new Dish("Pasta", 2200);
        Dish createdRest = dishService.create(DishUtil.asTo(new Dish(newDish)), 100010);
        newDish.setId(createdRest.getId());
        assertThat(createdRest).isEqualToIgnoringGivenFields(newDish, "menu", "restaurant");
    }

    @Test
    void createDuplicatedName() {
        Dish newRest = new Dish("CheeseBurger", 1001);
        assertThatExceptionOfType(IllegalRequestDataException.class)
                .isThrownBy(() -> {
                    dishService.create(DishUtil.asTo(new Dish(newRest)), 100010);
                });
    }
}