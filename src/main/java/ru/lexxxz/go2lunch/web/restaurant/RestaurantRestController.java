package ru.lexxxz.go2lunch.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.service.RestaurantService;
import ru.lexxxz.go2lunch.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/api/v1.0/admin/restaurants";

    @Autowired
    protected RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll(){
        log.info("Request to: " + REST_URL);
        return restaurantService.getAll();  }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable int id){
        log.info("Request to: " + REST_URL + "/" + id);
        return restaurantService.getTo(id);  }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant created = restaurantService.create(restaurantTo);
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
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        assureIdConsistent(restaurantTo, id);
        restaurantService.update(restaurantTo);
    }
}
