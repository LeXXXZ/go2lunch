package ru.lexxxz.go2lunch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexxxz.go2lunch.repository.jpa.VoteRepository;

@Service("voteService")
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    int countVotes(Integer restaurantId){
        return voteRepository.countAllByRestaurantId(restaurantId);
    }

}