package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.util.exception.OutOfTimeException;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.lexxxz.go2lunch.RestaurantTestData.REST1_ID;
import static ru.lexxxz.go2lunch.UserTestData.USER_ID;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Test
    void vote() {
        if (LocalTime.now().isAfter(LocalTime.parse("11:00"))) {
            assertThrows(OutOfTimeException.class, () -> voteService.vote(REST1_ID, USER_ID));
        } else {
            voteService.vote(REST1_ID, USER_ID);
            assertThat(voteService.isVoted(REST1_ID, USER_ID)).isTrue();
        }
    }
}