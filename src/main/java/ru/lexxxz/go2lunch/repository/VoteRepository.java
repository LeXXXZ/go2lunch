package ru.lexxxz.go2lunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.model.Vote;
import ru.lexxxz.go2lunch.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

Boolean existsByRestaurantIdAndUserIdAndDate(Integer id, Integer userId, LocalDate date);

Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate localDate);

    @Query("SELECT NEW ru.lexxxz.go2lunch.to.VoteTo(r.id, v.date) " +
            "FROM Vote v join v.restaurant r where v.user.id = :id")
    List<VoteTo> getAllByUserId(@Param("id") int authUserId);
}