package ru.lexxxz.go2lunch;

import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static ru.lexxxz.go2lunch.model.AbstractBaseEntity.START_SEQ;


public class RestaurantTestData {
    public static final int REST1_ID = START_SEQ + 2;
    public static final int REST2_ID = START_SEQ + 3;

    public static final Restaurant MACD = new Restaurant(REST1_ID, "MacDonalds");
    public static final Restaurant KFC = new Restaurant(REST2_ID, "KFC");

    public static final int VOTES_1 = 3;
    public static final int VOTES_2 = 1;

    public static final LocalDate DATE_1 = LocalDate.of(2019, 8, 16);
    public static final LocalDate DATE_2 = LocalDate.of(2019, 8, 17);

    public static final List<Dish> DISHES_FOR_MENU_100004 = List.of(new Dish(100009,"CheeseBurger", 1000), new Dish(100008,"BigMac", 2000));


}