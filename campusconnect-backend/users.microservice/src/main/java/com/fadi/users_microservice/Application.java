package com.fadi.users_microservice;

import com.fadi.users_microservice.entity.Role;
import com.fadi.users_microservice.entity.User;
import com.fadi.users_microservice.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BCryptPasswordEncoder getBCE() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    void init_users() {
        userService.addRole(new Role(null, "ADMIN"));
        userService.addRole(new Role(null, "USER"));

        userService.saveUser(new User(null, "admin", "123", true, null));
        userService.saveUser(new User(null, "nadhem", "123", true, null));
        userService.saveUser(new User(null, "yassine", "123", true, null));

        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("admin", "USER");
        userService.addRoleToUser("nadhem", "USER");
        userService.addRoleToUser("yassine", "USER");
    }
}