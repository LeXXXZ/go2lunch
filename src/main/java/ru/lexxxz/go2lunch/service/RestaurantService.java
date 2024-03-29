package ru.lexxxz.go2lunch.service;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Menu;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.repository.MenuRepository;
import ru.lexxxz.go2lunch.repository.RestaurantRepository;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.util.RestaurantUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

@Service("restaurantService")
@Transactional(readOnly = true)
public class RestaurantService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by("name"));
        log.info("All rests: {}", restaurants);
        return restaurants;
    }

    public RestaurantTo getTo(int id) throws NotFoundException {
        return RestaurantUtil.asTo(checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id));
    }

    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    @CacheEvict(value = "todaysMenu", allEntries = true)
    @Transactional
    public void delete(int id) throws NotFoundException {
        log.info("Delete rest with Id: {}", id);
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);

    }

    @CacheEvict(value = "todaysMenu", allEntries = true)
    @Transactional
    public void update(RestaurantTo restaurantTo) {
        assertNotNullEntity(restaurantTo);
        log.info("Update rest {}", restaurantTo);
        Restaurant restaurant = get(restaurantTo.getId());
        checkNotFoundWithId(restaurantRepository.save(RestaurantUtil.updateFromTo(restaurant, restaurantTo)), restaurantTo.getId());
    }

    @CacheEvict(value = "todaysMenu", allEntries = true)
    @Transactional
    public Restaurant create(RestaurantTo restaurantTo) {
        assertNotNullEntity(restaurantTo);
        log.info("Create rest {}", restaurantTo);
        return restaurantRepository.save(RestaurantUtil.createNewFromTo(restaurantTo));
    }

    @Cacheable("todaysMenu")
    public List<RestaurantTo> getRestsWithTodayMenu(){
        LocalDate today = LocalDate.now();
        List<RestaurantTo> todayRestaurants = restaurantRepository.getAllWithTodayMenu(today);
        List<Menu> todayMenus =  menuRepository.findAllByDate(today);
        for (Menu m : todayMenus) {
            for (RestaurantTo restaurantTo : todayRestaurants) {
                if (m.getRestaurant().getId().equals(restaurantTo.getId()))
                    restaurantTo.getTodayMenu().add(m);
            }
        }
         return todayRestaurants;

    }
}