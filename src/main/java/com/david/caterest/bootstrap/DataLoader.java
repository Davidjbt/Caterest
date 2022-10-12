package com.david.caterest.bootstrap;

import com.david.caterest.entity.User;
import com.david.caterest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    //https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User david = User.builder().userName("Davicito_Bow").build();

        userRepository.save(david);
    }
}

