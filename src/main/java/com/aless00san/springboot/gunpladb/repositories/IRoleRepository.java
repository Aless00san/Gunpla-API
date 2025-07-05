package com.aless00san.springboot.gunpladb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aless00san.springboot.gunpladb.entities.system.Role;

public interface IRoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
