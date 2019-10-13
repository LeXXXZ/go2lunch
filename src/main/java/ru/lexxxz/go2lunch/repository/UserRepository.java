package ru.lexxxz.go2lunch.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lexxxz.go2lunch.model.User;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int deleteById(@Param("id") int id);

    @Transactional
    @Modifying
    default boolean delete(int id){
        return  deleteById(id) != 0;
    }

    Optional<User> findById(int id);

    User getByEmail(String email);

    default List<User> getAll(){
        return findAll(SORT_NAME_EMAIL);
    };
}
