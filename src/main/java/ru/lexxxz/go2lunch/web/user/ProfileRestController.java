package ru.lexxxz.go2lunch.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.service.RestaurantService;
import ru.lexxxz.go2lunch.to.UserTo;
import ru.lexxxz.go2lunch.to.VoteTo;
import ru.lexxxz.go2lunch.web.SecurityUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/api/v1.0/profile";

    @Autowired
    protected RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(SecurityUtil.authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(SecurityUtil.authUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo) {
        super.update(userTo, SecurityUtil.authUserId());
    }

    //    User actions with restaurants votes

        //TODO Change response to RestaurantTo with vote
        //TODO add notfoundcheck for restaurant id
    @GetMapping(value = "/vote/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isRestaurantVoted(@PathVariable int id){
        return voteService.isVoted(id, SecurityUtil.authUserId());  }

        //TODO Add check for too late vote (after 16:00)
        @PutMapping(value = "/vote/{id}")
        @ResponseStatus(value = HttpStatus.OK)
        public ResponseEntity vote (@PathVariable int id) {
            voteService.vote(id, SecurityUtil.authUserId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getAllVotes(){
        return voteService.getAll(SecurityUtil.authUserId());
    }

}