package com.f5antos.tienda.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.f5antos.tienda.model.AppRole;
import com.f5antos.tienda.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
