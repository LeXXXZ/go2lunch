package ru.lexxxz.go2lunch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.Vote;
import ru.lexxxz.go2lunch.repository.RestaurantRepository;
import ru.lexxxz.go2lunch.repository.UserRepository;
import ru.lexxxz.go2lunch.repository.VoteRepository;
import ru.lexxxz.go2lunch.to.VoteTo;
import ru.lexxxz.go2lunch.util.exception.OutOfTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service("voteService")
public class VoteService {
private static final Logger log = LoggerFactory.getLogger(VoteService.class);
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepositoryRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepositoryRepository;
    }

    public Boolean isVoted(int restId, int authUserId) {
        return voteRepository.existsByRestaurantIdAndUserIdAndDate(restId, authUserId, LocalDate.now());
    }

    @Transactional
    public Vote vote(int restId, int authUserId) {
        Optional<Vote> vote = voteRepository.findByUserIdAndDate(authUserId, LocalDate.now());

        return voteRepository.save(vote.map(v -> {
            if (LocalTime.now().isBefore(LocalTime.of(11, 0, 0))) {
                v.setRestaurant(restaurantRepository.getOne(restId));
                return v;
            }
            throw new OutOfTimeException("Impossible to change your vote after 11-00");
        }).orElse(new Vote(LocalDate.now(), userRepository.findById(authUserId), restaurantRepository.getOne(restId))));
    }

    public List<VoteTo> getAll(int authUserId) {
        log.info("Votes for user {}", authUserId);
        return voteRepository.getAllByUserId(authUserId);
    }
}