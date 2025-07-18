package com.aless00san.springboot.gunpladb.services;

import java.util.List;

import com.aless00san.springboot.gunpladb.entities.system.Role;
import com.aless00san.springboot.gunpladb.entities.system.User;

public interface IUserService {
    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);

    List<Role> findRolesByUsername(String username);
}
