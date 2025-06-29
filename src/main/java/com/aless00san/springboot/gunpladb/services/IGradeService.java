package com.aless00san.springboot.gunpladb.services;

import java.util.List;

import com.aless00san.springboot.gunpladb.entities.Grade;

public interface IGradeService {

    public void delete(Grade grade);

    public Grade findByName(String name);

    public Grade create(Grade grade);

    public Grade update(Grade old, Grade newGrade);

    public List<Grade> findAll();

    public Grade findById(Long id);

}
