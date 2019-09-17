package ru.lexxxz.go2lunch.to;

import ru.lexxxz.go2lunch.model.Dish;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

public class MenuTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int restaurantId;
    private String restaurantName;
    private int votes;
    private List<Dish> dishes;

    public MenuTo(){
    }

    public MenuTo(int restaurantId, String restaurantName, int votes, int id) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.votes = votes;
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MenuTo.class.getSimpleName() + "[", "]")
                .add("restaurantId=" + restaurantId)
                .add("restaurantName='" + restaurantName + "'")
                .add("votes=" + votes)
                .add("id=" + id)
                .add("dishes=" + dishes)
                .toString();
    }
}
