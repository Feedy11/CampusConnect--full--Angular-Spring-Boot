package com.fadi.users_microservice.repos;

import com.fadi.users_microservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
