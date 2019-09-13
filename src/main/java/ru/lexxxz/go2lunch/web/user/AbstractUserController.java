package ru.lexxxz.go2lunch.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.service.MenuService;
import ru.lexxxz.go2lunch.service.RestaurantService;
import ru.lexxxz.go2lunch.service.UserService;
import ru.lexxxz.go2lunch.service.VoteService;
import ru.lexxxz.go2lunch.to.UserTo;
import ru.lexxxz.go2lunch.util.UserUtil;

import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assureIdConsistent;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserService service;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected VoteService voteService;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(UserTo userTo) {
        return create(UserUtil.createNewFromTo(userTo));
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }
}