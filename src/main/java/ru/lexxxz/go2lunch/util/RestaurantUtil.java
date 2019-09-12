package ru.lexxxz.go2lunch.util;

import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.to.RestaurantTo;

public class RestaurantUtil {

    public static RestaurantTo asTo(Restaurant restaurant, int votes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), votes);
    }

}