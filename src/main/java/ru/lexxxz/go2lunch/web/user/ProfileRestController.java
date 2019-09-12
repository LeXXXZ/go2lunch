package ru.lexxxz.go2lunch.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.Dish;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.to.UserTo;
import ru.lexxxz.go2lunch.web.SecurityUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/api/v1.0/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(SecurityUtil.authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(SecurityUtil.authUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo) {
        super.update(userTo, SecurityUtil.authUserId());
    }

    //    User actions with restaurants votes

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getAllRestaurantsWithVotes(){
        return restaurantService.getAllWithVotes();  }



    //    User actions with restaurants menu

    @GetMapping(value = "/restaurants/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getTodayMenu(@PathVariable int id){
        return menuService.getTodayMenuDishes(id);  }

    @GetMapping(value = "/restaurants/{id}/menu/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenuByDay(@PathVariable int id, @PathVariable String date){
        return menuService.getDayMenuDishes(LocalDate.parse(date), id);  }
}