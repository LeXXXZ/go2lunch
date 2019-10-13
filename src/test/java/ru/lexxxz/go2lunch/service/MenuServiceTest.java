package ru.lexxxz.go2lunch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ru.lexxxz.go2lunch.RestaurantTestData.DATE_1;
import static ru.lexxxz.go2lunch.RestaurantTestData.MACD;
import static ru.lexxxz.go2lunch.RestaurantTestData.MENU1_OF_REST_1;
import static ru.lexxxz.go2lunch.RestaurantTestData.MENUS_OF_REST_1;
import static ru.lexxxz.go2lunch.RestaurantTestData.REST1_ID;
import static ru.lexxxz.go2lunch.RestaurantTestData.getNewMenu;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

class MenuServiceTest extends AbstractServiceTest{

    @Autowired
    protected MenuService menuService;

    @Test
    void getByRestIdAllSortedByDate() {
        List<Menu> all = menuService.getAll(REST1_ID);
        assertThat(all)
//            .usingElementComparatorIgnoringFields("restaurant")
            .isEqualTo(MENUS_OF_REST_1);
    }

    @Test
    void getById() {
        Menu menu = menuService.get(100021, REST1_ID);
        assertThat(menu).isEqualToIgnoringGivenFields(MENU1_OF_REST_1,"restaurant");
    }

    @Test
    void getNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> { menuService.get(1, REST1_ID); });
        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> { menuService.get(100021, 1); });
    }

    @Test
    void update() {
        Menu updated = new Menu(MENU1_OF_REST_1.getId(),"BigMac", 2000 , DATE_1);
        updated.setDate(LocalDate.now().plusDays(1));
        updated.setName("SmallMac");
        updated.setRestaurant(MACD);
        menuService.update(updated, REST1_ID, updated.getId());
        assertThat(menuService.get(MENU1_OF_REST_1.getId(), REST1_ID))
            .isEqualToIgnoringGivenFields(updated, "restaurant");

    }

    @Test
    void create() {
        Menu newMenu = getNewMenu();
        Menu createdMenu = menuService.create(newMenu, REST1_ID);
        newMenu.setId(createdMenu.getId());
        assertThat(createdMenu).isEqualToIgnoringGivenFields(newMenu,"restaurant");
    }

  @Test
    void createWithDuplicatedName() {
        assertThatExceptionOfType(IllegalRequestDataException.class)
                .isThrownBy(() -> { menuService.create(new Menu("BigMac", 3000 , DATE_1),REST1_ID); });
    }
}