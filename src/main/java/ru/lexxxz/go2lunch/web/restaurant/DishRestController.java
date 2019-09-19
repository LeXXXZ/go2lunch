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
import ru.lexxxz.go2lunch.to.DishTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishRestController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String MENU_URL = "/api/v1.0/admin/restaurants/{id}/dishes";

    @Autowired
    protected DishService dishService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishTo> getAllDishesForRest(@PathVariable(name = "id") int restId){
        return dishService.getAll(restId);
    }

    @GetMapping(value = "/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishTo get(@PathVariable(name = "id") int restId, @PathVariable(name = "dishId") int dishId){
        return dishService.getTo(dishId, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo, @PathVariable(name = "id") int restId) {
        checkNew(dishTo);
        log.info("Create {} for restaurant id: {}", dishTo, restId);
        Dish created = dishService.create(dishTo, restId);
        String url = MENU_URL + "/" + created.getId();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable(name = "id") int restId, @PathVariable int dishId) {
        dishService.update(dishTo, restId, dishId);
    }

}
