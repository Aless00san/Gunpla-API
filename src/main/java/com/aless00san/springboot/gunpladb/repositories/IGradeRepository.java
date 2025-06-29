package com.aless00san.springboot.gunpladb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aless00san.springboot.gunpladb.entities.Grade;

public interface IGradeRepository extends CrudRepository<Grade, Long> {
    Grade findByName(String name);
    Optional<Grade> findById(Long id);
}
