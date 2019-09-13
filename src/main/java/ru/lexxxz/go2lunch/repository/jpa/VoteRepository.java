package ru.lexxxz.go2lunch.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

Integer countAllByRestaurantId(Integer id);

Boolean existsByRestaurantIdAndUserIdAndDate(Integer id, Integer userId, LocalDate date);

Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate localDate);
}