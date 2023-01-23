package com.group.contestback;

import com.group.contestback.models.AppUser;
import com.group.contestback.services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ContestBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContestBackApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService userService) {
        return args -> {
            if(userService.getUsers().size() == 0) {
                AppUser user = new AppUser(1, "Ivan","ivan","ivanovich",
                        "ivanLogin", "", "", "reshat.sultan@yandex.ru",1,1);
                userService.saveAppUser(user);
            }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
