package com.aless00san.springboot.gunpladb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aless00san.springboot.gunpladb.entities.system.Role;
import com.aless00san.springboot.gunpladb.entities.system.User;
import com.aless00san.springboot.gunpladb.repositories.IRoleRepository;
import com.aless00san.springboot.gunpladb.repositories.IUserRepository;

@Service
public class UserServiceJpa implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        userRole.ifPresent(roles::add);

        if (user.isSuperuser()) {
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            adminRole.ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
