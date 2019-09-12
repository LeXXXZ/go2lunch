package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Restaurant;
import ru.lexxxz.go2lunch.repository.RestaurantRepository;
import ru.lexxxz.go2lunch.repository.jpa.VoteRepository;
import ru.lexxxz.go2lunch.to.RestaurantTo;

import java.util.ArrayList;
import java.util.List;

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
        List<Restaurant> restaurants = restaurantRepository.getAll();
        log.info("All rests: {}", restaurants);
        for (Restaurant r : restaurants) {
            log.info("Count votes for rest {}", r.toString());
            restaurantToList.add(new RestaurantTo(r.id(), r.getName(), voteRepository.countAllByRestaurantId(r.getId())));
        }
        return restaurantToList;
    }

}