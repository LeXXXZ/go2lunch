package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.lexxxz.go2lunch.RestaurantTestData.*;

class MenuServiceTest extends AbstractServiceTest{

    @Autowired
    protected MenuService menuService;

    @Test
    void getAllSorted() {
        List<Menu> all = menuService.getAll(REST1_ID);
        assertThat(all).usingElementComparatorIgnoringFields("dishes", "restaurant").isEqualTo(List.of(MENU2_OF_REST_1, MENU1_OF_REST_1));
    }

    @Test
    void getByRestIdAndDate() {
        Menu menu = menuService.get(DATE_1, REST1_ID);
        assertThat(menu).isEqualToIgnoringGivenFields(MENU1_OF_REST_1,"dishes", "restaurant");
    }

    @Test
    void getNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> { menuService.get(DATE_1, 1); });
    }

    @Test
    void update() {
        Menu updated = new Menu(MENU1_OF_REST_1.getId(), DATE_1);
        updated.setDate(LocalDate.now());
        menuService.update(updated, REST1_ID, DATE_1);
        assertThat(menuService.get(LocalDate.now(), REST1_ID)).isEqualToIgnoringGivenFields(updated, "dishes", "restaurant");

    }

    @Test
    void create() {
        Menu newMenu = new Menu(null, LocalDate.now());
        Menu createdMenu = menuService.create(new Menu(LocalDate.now()), REST1_ID);
        newMenu.setId(createdMenu.getId());
        assertThat(createdMenu).isEqualToIgnoringGivenFields(newMenu, "dishes", "restaurant");
    }

    @Test
    void createWithDuplicatedDate() {
        Menu newRest = new Menu(null, DATE_1);
        assertThatExceptionOfType(IllegalRequestDataException.class)
                .isThrownBy(() -> { menuService.create(new Menu(DATE_1),REST1_ID); });
    }
}