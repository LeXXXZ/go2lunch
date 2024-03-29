package ru.lexxxz.go2lunch.model;

import java.time.LocalDate;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = "date", name = "menus_unique_date_idx")})
public class Menu extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    @NotNull
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT today()")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    private Restaurant restaurant;

    public Menu(){
    }

    public Menu(Integer id, String name, Integer price,LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

  public Menu(String name, Integer price, LocalDate date) {
    this(null, name, price, date);
  }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Menu.class.getSimpleName() + "[", "]")
                .add("price=" + price)
                .add("date=" + date)
                .add("restaurant=" + restaurant.getId())
                .add("name='" + name + "'")
                .add("id=" + id)
                .toString();
    }
}
