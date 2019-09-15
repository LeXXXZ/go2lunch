package ru.lexxxz.go2lunch.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.service.MenuService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MenuRestController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String MENU_URL = "/api/v1.0/restaurants/{id}/menu";

    @Autowired
    protected MenuService menuService;

    @GetMapping
    public List<Menu> getAll(@PathVariable(name = "id") int restaurantId){
        log.info("Request to: " + MENU_URL);
        return menuService.getAll(restaurantId);  }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable(name = "id") int restaurantId) {
        checkNew(menu);
        log.info("Create {} for rest {}", menu, restaurantId);
        Menu created = menuService.create(menu, restaurantId);

        String url = MENU_URL + menu.getDate().toString();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{date}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable(name = "id") int restaurantId, @PathVariable String date) {
        menuService.update(menu, restaurantId, LocalDate.parse(date));
    }

}
