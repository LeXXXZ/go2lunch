package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.Dish;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lexxxz.go2lunch.RestaurantTestData.*;

class MenuServiceTest extends AbstractServiceTest{

    @Autowired
    protected MenuService menuService;

    @Test
    void getDayMenuDishes() {
        List<Dish> dayDishes = menuService.getDayMenuDishes(DATE_1, REST1_ID);
        assertThat(dayDishes).isEqualTo(DISHES_FOR_MENU_100004);
    }

    @Test
    void getTodayMenuDishes() {
    assertThat(menuService.getDayMenuDishes(LocalDate.now(), REST1_ID)).isEqualTo(List.of());
    }
}