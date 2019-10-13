package ru.lexxxz.go2lunch.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import ru.lexxxz.go2lunch.model.Menu;

public class RestaurantTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    private Collection<Menu> todayMenu;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
        this.todayMenu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Menu> getTodayMenu() {
        return todayMenu;
    }

    public void setTodayMenu(List<Menu> todayMenu) {
        this.todayMenu = todayMenu;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RestaurantTo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("menus= " + todayMenu)
                .toString();
    }
}

