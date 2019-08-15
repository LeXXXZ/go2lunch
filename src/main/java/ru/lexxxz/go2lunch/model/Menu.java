package ru.lexxxz.go2lunch.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = "date", name = "menus_unique_date_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public Menu(){
    }

    public Menu(@NotNull LocalDate date) {
        this(null, date);
    }

    public Menu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @OrderBy("date DESC")
    protected List<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    private Restaurant restaurant;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
