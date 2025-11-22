package com.fadi.users_microservice.service;

import com.fadi.users_microservice.entity.Role;
import com.fadi.users_microservice.entity.User;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
}

