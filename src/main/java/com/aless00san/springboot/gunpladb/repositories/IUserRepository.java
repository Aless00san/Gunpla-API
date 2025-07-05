package com.aless00san.springboot.gunpladb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aless00san.springboot.gunpladb.entities.system.User;

public interface IUserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
