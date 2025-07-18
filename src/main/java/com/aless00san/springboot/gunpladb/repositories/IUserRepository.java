package com.aless00san.springboot.gunpladb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aless00san.springboot.gunpladb.entities.system.Role;
import com.aless00san.springboot.gunpladb.entities.system.User;

public interface IUserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT u.roles FROM User u WHERE u.username = ?1")
    List<Role> findRolesByUsername(String username);
}
