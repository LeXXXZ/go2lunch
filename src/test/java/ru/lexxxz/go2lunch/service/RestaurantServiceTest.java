package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.util.RestaurantUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.lexxxz.go2lunch.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest{

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void getAllWithVotes() {
        Iterable<RestaurantTo> restaurantTos = restaurantService.getAllWithVotes();
        assertThat(restaurantTos).usingElementComparatorIgnoringFields("menus")
                .isEqualTo(List.of(RestaurantUtil.asTo(KFC, VOTES_2), RestaurantUtil.asTo(MACD, VOTES_1)));
    }

    @Test
    void getAllSorted() {
        List<Restaurant> all = restaurantService.getAll();
        assertThat(all).usingElementComparatorIgnoringFields("menus").isEqualTo(List.of(KFC, MACD));
    }

    @Test
    void getById() {
        Restaurant restaurant = restaurantService.get(REST1_ID);
        assertThat(restaurant).isEqualToIgnoringGivenFields(MACD,"menus");
    }

    @Test
    void getNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> { restaurantService.get(1); });
    }

    @Test
    void delete() {
        restaurantService.delete(REST1_ID);
        assertThat(restaurantService.getAll()).usingElementComparatorIgnoringFields("menus").isEqualTo(List.of(KFC));
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
        restaurantService.update(new Restaurant(updated));
        assertThat(restaurantService.get(REST1_ID)).isEqualToIgnoringGivenFields(updated, "menus");

    }

    @Test
    void create() {
        Restaurant newRest = new Restaurant(null, "MamaRoma");
        Restaurant createdRest = restaurantService.create(new Restaurant(newRest));
        newRest.setId(createdRest.getId());
        assertThat(createdRest).isEqualToIgnoringGivenFields(newRest, "menus");
    }

    @Test
    void createDuplicatedName() {
        Restaurant newRest = new Restaurant(null, "KFC");
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> { restaurantService.create(new Restaurant(newRest)); });
    }
}