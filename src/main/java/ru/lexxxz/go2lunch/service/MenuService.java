package ru.lexxxz.go2lunch.service;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.repository.MenuRepository;
import ru.lexxxz.go2lunch.repository.RestaurantRepository;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

@Service("menuService")
@Transactional(readOnly = true)
public class MenuService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll(int restaurantId) {
        checkNotFoundRestaurant(restaurantId);
        return menuRepository.findAllByRestaurantIdOrderByDateDesc(restaurantId);
    }

    public List<Menu> getAllByDate(LocalDate date, int restaurantId) {
        checkNotFoundRestaurant(restaurantId);
        return menuRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    public Menu get(int menuId, int restaurantId) {
        return menuRepository.findByRestaurantIdAndId(restaurantId, menuId).orElseThrow(() -> new NotFoundException("No such menu"));
    }

    @CacheEvict(value = "todaysMenu", allEntries = true)
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        assertNotNullEntity(menu);
        if (menu.getRestaurant() != null && menu.getRestaurant().getId() != restaurantId) {
            throw new IllegalRequestDataException("Menu couldn't belong to restaurant: " + restaurantId);
        }
        if (menu.getDate() == null) {
            menu.setDate(LocalDate.now());
        }

        if (!menuRepository.existsByRestaurantIdAndDateAndName(restaurantId , menu.getDate(), menu.getName())) {
            menu.setRestaurant(restaurantRepository.getOne(restaurantId));
            return menuRepository.save(menu);
        }
        else throw new IllegalRequestDataException("Menu already exists");
    }

    @CacheEvict(value = "todaysMenu", allEntries = true)
    @Transactional
    public void update(Menu menu, int restaurantId, int id) {
        assertNotNullEntity(menu);
        if (menuRepository.existsByIdAndRestaurantId(id, restaurantId)) {
        menuRepository.save(menu);}
        else throw new IllegalRequestDataException("No menu with id: " + id + " for restaurant id: " + restaurantId);
    }

    private void checkNotFoundRestaurant(int restaurantId) {
        checkNotFoundWithId(restaurantRepository.existsById(restaurantId), restaurantId);
    }
}
