package ru.lexxxz.go2lunch.web.restaurant;

import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNew;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.service.MenuService;

@RestController
@RequestMapping(value = MenuRestController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String MENU_URL = "/api/v1.0/admin/restaurants/{id}/menus";

    @Autowired
    protected MenuService menuService;

    @GetMapping
    public List<Menu> getAll(@PathVariable(name = "id") int restaurantId){
        log.info("Request to: " + MENU_URL);
        return menuService.getAll(restaurantId);  }

    @GetMapping(value = "/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable(name = "id") int restaurantId, @PathVariable (name = "menuId") int menuId){
        log.info("Request to: " + MENU_URL+ "/" + menuId);
        return menuService.get(menuId, restaurantId);  }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable(name = "id") int restaurantId) {
        checkNew(menu);
        log.info("Create {} for rest {}", menu, restaurantId);
        Menu created = menuService.create(menu, restaurantId);

        String url = MENU_URL + menu.getId().toString();
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable(name = "id") int restaurantId, @PathVariable (name = "menuId") int menuId) {
        menuService.update(menu, restaurantId, menuId);
    }

}
