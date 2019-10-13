package ru.lexxxz.go2lunch;

import static ru.lexxxz.go2lunch.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDate;
import java.util.List;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.model.Restaurant;


public class RestaurantTestData {
    public static final int REST1_ID = START_SEQ + 10;
    public static final int REST2_ID = START_SEQ + 11;

    public static final Restaurant MACD = new Restaurant(REST1_ID, "MacDonalds");
    public static final Restaurant KFC = new Restaurant(REST2_ID, "KFC");

    public static final int VOTES_1 = 3;
    public static final int VOTES_2 = 1;

    public static final LocalDate DATE_1 = LocalDate.of(2019, 8, 16);
    public static final LocalDate DATE_2 = LocalDate.now();

    public static final Menu MENU1_OF_REST_1 = new Menu(START_SEQ + 21, "BigMac", 2000 , DATE_1);
    public static final Menu MENU2_OF_REST_1 = new Menu(START_SEQ + 22, "CheeseBurger", 1000 , DATE_1);
    public static final Menu MENU3_OF_REST_1 = new Menu(START_SEQ + 23, "Fries", 1000 , DATE_1);
    public static final Menu MENU4_OF_REST_1 = new Menu(START_SEQ + 26 , "BigMac", 2000, DATE_2);
    public static final Menu MENU5_OF_REST_1 = new Menu(START_SEQ + 27 , "BigMac2", 1000, DATE_2);
    public static final Menu MENU6_OF_REST_1 = new Menu(START_SEQ + 28 , "Fries", 1000, DATE_2);

    public static final List<Menu> MENUS_OF_REST_1 = List.of(MENU4_OF_REST_1 ,MENU5_OF_REST_1, MENU6_OF_REST_1,
        MENU1_OF_REST_1, MENU2_OF_REST_1, MENU3_OF_REST_1);

    public static Menu getNewMenu() {
      return new Menu("NewBigMac", 2222 , LocalDate.now().plusDays(1));
    }
}