package com.carddex.sims2.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carddex.sims2.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
