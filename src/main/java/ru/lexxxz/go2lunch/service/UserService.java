package ru.lexxxz.go2lunch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.repository.UserRepository;
import ru.lexxxz.go2lunch.to.UserTo;
import ru.lexxxz.go2lunch.util.UserUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import java.util.List;

import static ru.lexxxz.go2lunch.util.ValidationUtil.*;

@Service("userService")
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        assertNotNullEntity(user);
        return repository.save(user);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) throws NotFoundException {
        assertNotNullEntity(user);
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        checkNotFoundWithId(repository.save(UserUtil.updateFromTo(user, userTo)), user.getId());
    }

    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }
}