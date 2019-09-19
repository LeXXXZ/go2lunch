package ru.lexxxz.go2lunch.to;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

public class VoteTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer restaurantId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public VoteTo() {
    }

    public VoteTo(Integer restaurantId, @NotNull LocalDate date) {
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VoteTo.class.getSimpleName() + "[", "]")
                .add("date=" + date)
                .add("restaurantId=" + restaurantId)
                .toString();
    }
}

