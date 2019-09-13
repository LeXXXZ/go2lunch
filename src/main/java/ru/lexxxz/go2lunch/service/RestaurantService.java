package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.repository.jpa.RestaurantRepository;
import ru.lexxxz.go2lunch.repository.jpa.VoteRepository;
import ru.lexxxz.go2lunch.to.RestaurantTo;
import ru.lexxxz.go2lunch.util.RestaurantUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<RestaurantTo> getAllWithVotes() {
        List<RestaurantTo> restaurantToList = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        log.info("All rests: {}", restaurants);
        for (Restaurant r : restaurants) {
            log.info("Count votes for rest {}", r.toString());
            restaurantToList.add(RestaurantUtil.asTo(r, voteRepository.countAllByRestaurantId(r.getId())));
        }
        restaurantToList.sort(Comparator.comparing(RestaurantTo::getName));
        return restaurantToList;
    }

    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAllByIdNotNullOrderByName();
        log.info("All rests: {}", restaurants);
        return restaurants;
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }

    @Transactional
    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }
}