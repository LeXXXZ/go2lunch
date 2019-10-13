package ru.lexxxz.go2lunch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.lexxxz.go2lunch.UserTestData.ADMIN;
import static ru.lexxxz.go2lunch.UserTestData.ADMIN_ID;
import static ru.lexxxz.go2lunch.UserTestData.USER;
import static ru.lexxxz.go2lunch.UserTestData.USER_ID;

import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.lexxxz.go2lunch.model.Role;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.repository.JpaUtil;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

class UserServiceTest extends AbstractServiceTest{

    @Autowired
    protected UserService service;

    @Autowired
    private CacheManager cacheManager;

    private JpaUtil jpaUtil;

    @Autowired(required = false)
    private void setJpaUtil(JpaUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, Collections.singleton(Role.ROLE_USER));
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertThat(created).isEqualToIgnoringGivenFields(newUser, "password", "votes");
        assertThat(service.getAll()).usingElementComparatorIgnoringFields("password", "votes").isEqualTo(List.of(ADMIN, newUser, USER));
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER_ID);
        assertThat(service.getAll()).usingElementComparatorIgnoringFields("password", "votes").isEqualTo(List.of(ADMIN));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    public void get() {
        User user = service.get(ADMIN_ID);
        assertThat(user).isEqualToIgnoringGivenFields(ADMIN, "password", "votes");
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertThat(user).isEqualToIgnoringGivenFields(ADMIN, "password", "votes");
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(new User(updated));
        assertThat(service.get(USER_ID)).isEqualToIgnoringGivenFields(updated, "password", "votes");
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        assertThat(all).usingElementComparatorIgnoringFields("password", "votes").isEqualTo(List.of(ADMIN, USER));
    }

    @Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new User(null, " ", "mail@yandex.ru", "password",Role.ROLE_ADMIN,Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_ADMIN)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_ADMIN,Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", true, Collections.emptySet())), ConstraintViolationException.class);
    }
}