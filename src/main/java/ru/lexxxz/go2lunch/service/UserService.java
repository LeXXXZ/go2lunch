package ru.lexxxz.go2lunch.service;

import static ru.lexxxz.go2lunch.util.ValidationUtil.assertNotNullEntity;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFound;
import static ru.lexxxz.go2lunch.util.ValidationUtil.checkNotFoundWithId;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lexxxz.go2lunch.AuthorizedUser;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.repository.UserRepository;
import ru.lexxxz.go2lunch.to.UserTo;
import ru.lexxxz.go2lunch.util.UserUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        assertNotNullEntity(user);
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) throws NotFoundException {
        assertNotNullEntity(user);
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        checkNotFoundWithId(repository.save(UserUtil.updateFromTo(user, userTo)), user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }


}