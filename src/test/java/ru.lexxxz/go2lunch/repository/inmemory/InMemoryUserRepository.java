package ru.lexxxz.go2lunch.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.lexxxz.go2lunch.UserTestData;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.lexxxz.go2lunch.UserTestData.ADMIN;
import static ru.lexxxz.go2lunch.UserTestData.USER;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public void init() {
        entryMap.clear();
        entryMap.put(UserTestData.USER_ID, USER);
        entryMap.put(UserTestData.ADMIN_ID, ADMIN);
    }

    @Override
    public List<User> getAll() {
        init();
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}