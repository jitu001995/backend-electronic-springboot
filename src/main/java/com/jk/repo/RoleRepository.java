package com.jk.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jk.entities.Role;

public interface RoleRepository extends JpaRepository<Role,String> {
}
