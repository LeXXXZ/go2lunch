package ru.lexxxz.go2lunch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.lexxxz.go2lunch.RestaurantTestData.KFC;
import static ru.lexxxz.go2lunch.RestaurantTestData.MACD;
import static ru.lexxxz.go2lunch.RestaurantTestData.REST1_ID;
import static ru.lexxxz.go2lunch.RestaurantTestData.TODAYS_MENUS_OF_REST_1;
import static ru.lexxxz.go2lunch.RestaurantTestData.TODAYS_MENUS_OF_REST_2;
import static ru.lexxxz.go2lunch.util.RestaurantUtil.asTo;

import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

class RestaurantServiceTest extends AbstractServiceTest{

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void getAllSorted() {
        List<Restaurant> all = restaurantService.getAll();
        assertThat(all).containsExactly(KFC, MACD).isSortedAccordingTo(Comparator.comparing(Restaurant::getName));
    }

    @Test
    void todayMenus(){
        List<RestaurantTo> all = restaurantService.getRestsWithTodayMenu();
        RestaurantTo rest1 = asTo(MACD);
        TODAYS_MENUS_OF_REST_1.forEach(m1 -> m1.setRestaurant(MACD));
        rest1.setTodayMenu(TODAYS_MENUS_OF_REST_1);
        RestaurantTo rest2 = asTo(KFC);
        TODAYS_MENUS_OF_REST_2.forEach(m2 -> m2.setRestaurant(KFC));
        rest2.setTodayMenu(TODAYS_MENUS_OF_REST_2);
        assertThat(all.get(0)).isEqualToIgnoringGivenFields(rest1, "votes");
        assertThat(all.get(1)).isEqualToIgnoringGivenFields(rest2, "votes");
    }

    @Test
    void getById() {
        Restaurant restaurant = restaurantService.get(REST1_ID);
        assertThat(restaurant).isEqualToIgnoringGivenFields(MACD,"menus", "votes");
    }

    @Test
    void getNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> { restaurantService.get(1); });
    }

    @Test
    void delete() {
        restaurantService.delete(REST1_ID);
        assertThat(restaurantService.getAll()).usingElementComparatorIgnoringFields("menus", "votes").isEqualTo(List.of(KFC));
    }

    @Test
    void deleteNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> { restaurantService.delete(1); });
    }

    @Test
    void update() {
        Restaurant updated = new Restaurant(MACD);
        updated.setName("UpdatedName");
        restaurantService.update(asTo(new Restaurant(updated)));
        assertThat(restaurantService.get(REST1_ID)).isEqualToIgnoringGivenFields(updated, "menus", "votes");

    }

    @Test
    void create() {
        Restaurant newRest = new Restaurant(null, "MamaRoma");
        Restaurant createdRest = restaurantService.create(asTo(new Restaurant(newRest)));
        newRest.setId(createdRest.getId());
        assertThat(createdRest).isEqualToIgnoringGivenFields(newRest, "menus", "dishes", "votes");
    }

    @Test
    void createDuplicatedName() {
        Restaurant newRest = new Restaurant(null, "KFC");
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> { restaurantService.create(asTo(new Restaurant(newRest))); });
    }
}