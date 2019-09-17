package ru.lexxxz.go2lunch.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishRestController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String MENU_URL = "/api/v1.0/dishes/{menuId}";

    @Autowired
    protected DishService dishService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllDishesForMenu(@PathVariable int menuId){
        return dishService.getAll(menuId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int menuId) {
        checkNew(dish);
        log.info("Create {} for menu id: {}", dish, menuId);
        Dish created = dishService.create(dish, menuId);

        String url = MENU_URL + "/{dishId}";
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int menuId, @PathVariable int dishId) {
        dishService.update(dish, menuId, dishId);
    }

}
