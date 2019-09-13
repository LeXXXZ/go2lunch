package ru.lexxxz.go2lunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.Vote;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lexxxz.go2lunch.RestaurantTestData.REST1_ID;
import static ru.lexxxz.go2lunch.UserTestData.USER_ID;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    protected VoteService voteService;

    @Test
    void isVoted() {
        assertThat(voteService.isVoted(REST1_ID, USER_ID)).isFalse();
        voteService.vote(REST1_ID,USER_ID);
        assertThat(voteService.isVoted(REST1_ID, USER_ID)).isTrue();
    }

    @Test
    void vote() {
        Vote todayVote = voteService.vote(REST1_ID,USER_ID);
        assertThat(voteService.isVoted(REST1_ID, USER_ID)).isTrue();
    }

    @Test
    void countVotes() {
        assertThat(voteService.countVotes(REST1_ID)).isEqualTo(3);
    }
}