package ru.lexxxz.go2lunch.web.voting;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lexxxz.go2lunch.service.MenuService;
import ru.lexxxz.go2lunch.service.RestaurantService;
import ru.lexxxz.go2lunch.to.RestaurantTo;

@RestController
@RequestMapping(value = TodayMenuRestController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class TodayMenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String MENU_URL = "/api/v1.0/today-menu";

    @Autowired
    protected MenuService menuService;
    @Autowired
    protected RestaurantService restaurantService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAllWithTodayMenu() {
        log.info("Request to: " + MENU_URL);
        return restaurantService.getRestsWithTodayMenu();
    }
}
