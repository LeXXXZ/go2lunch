package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.repository.RestaurantRepository;
import ru.lexxxz.go2lunch.repository.VoteRepository;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.util.RestaurantUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
@Transactional(readOnly = true)
public class RestaurantService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
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

    @Transactional
    public void delete(int id) throws NotFoundException {
        log.info("Delete rest with Id: {}", id);
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);

    }

    @Transactional
    public void update(RestaurantTo restaurantTo) {
        assertNotNullEntity(restaurantTo);
        log.info("Update rest {}", restaurantTo);
        Restaurant restaurant = get(restaurantTo.getId());
        checkNotFoundWithId(restaurantRepository.save(RestaurantUtil.updateFromTo(restaurant, restaurantTo)), restaurantTo.getId());
    }

    @Transactional
    public Restaurant create(RestaurantTo restaurantTo) {
        assertNotNullEntity(restaurantTo);
        log.info("Create rest {}", restaurantTo);
        return restaurantRepository.save(RestaurantUtil.createNewFromTo(restaurantTo));
    }
}