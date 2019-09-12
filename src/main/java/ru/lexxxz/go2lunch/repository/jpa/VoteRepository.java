package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

Integer countAllByRestaurantId(Integer id);

}