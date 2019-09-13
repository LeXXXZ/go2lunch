package ru.lexxxz.go2lunch.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.service.MenuService;
import ru.lexxxz.go2lunch.service.RestaurantService;
import ru.lexxxz.go2lunch.service.VoteService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/api/v1.0/restaurants";

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected VoteService voteService;

    //    Actions with restaurants
    @GetMapping
    public List<Restaurant> getAll(){
        log.info("Request to: " + REST_URL);
        return restaurantService.getAll();  }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable int id){
        log.info("Request to: " + REST_URL + "/" + id);
        return restaurantService.get(id);  }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = new Restaurant(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }


    //    Actions with restaurants menu
    @GetMapping(value = "/restaurants/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getTodayMenu(@PathVariable int id){
        return menuService.getTodayMenuDishes(id);  }

    @GetMapping(value = "/restaurants/{id}/menu/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenuByDay(@PathVariable int id, @PathVariable String date){
        return menuService.getDayMenuDishes(LocalDate.parse(date), id);  }
}
