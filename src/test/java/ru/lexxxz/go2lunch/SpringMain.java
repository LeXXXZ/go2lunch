package ru.lexxxz.go2lunch;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.lexxxz.go2lunch.model.Role;
import ru.lexxxz.go2lunch.model.User;
import ru.lexxxz.go2lunch.repository.UserRepository;
import ru.lexxxz.go2lunch.repository.jpa.UserRepositoryImpl;
import ru.lexxxz.go2lunch.service.UserService;

import java.util.Collections;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            UserRepository userRepository = appCtx.getBean(UserRepositoryImpl.class);
            List<User> users = userRepository.getAll();
            System.out.println("---------------------------------");
            for (User u : users) {
                System.out.println(u.toString());
            }
            System.out.println("---------------------------------");
            UserService userService = appCtx.getBean(UserService.class);

            System.out.println("---------------------------------");
//            userService.create(new User(null, "jhjv", "mail@yandex.ru", "sdfdsfsdf", true, Collections.emptySet()));
            userService.create(new User(null, "jhjv", "mail1@yandex.ru", "sdfdsfsdf", true, Collections.singleton(Role.ROLE_USER)));
            System.out.println("---------------------------------");

            users = userRepository.getAll();
            for (User u : users) {
                System.out.println(u.toString());
            }
            System.out.println("---------------------------------");
//            User userByEmail;
//            try {
//                userByEmail =userService.getByEmail("a@gmail.com");
//                System.out.println(userByEmail.toString());
//            }
//            catch (NotFoundException e){
//            System.out.println(e);
//            }
//            System.out.println("---------------------------------");
//            userService.delete(100000);
        }
    }
}
