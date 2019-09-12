package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.util.RestaurantUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lexxxz.go2lunch.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest{

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void getAllWithVotes() {
        Iterable<RestaurantTo> restaurantTos = restaurantService.getAllWithVotes();
        assertThat(restaurantTos).usingElementComparatorIgnoringFields("menu")
                .isEqualTo(List.of(RestaurantUtil.asTo(KFC, VOTES_2), RestaurantUtil.asTo(MACD, VOTES_1)));
    }

}