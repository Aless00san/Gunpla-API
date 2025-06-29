package com.aless00san.springboot.gunpladb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aless00san.springboot.gunpladb.entities.Grade;
import com.aless00san.springboot.gunpladb.repositories.IGradeRepository;

@Service
public class GradeServiceJpa implements IGradeService {

    @Autowired
    IGradeRepository repository;

    @Override
    public void delete(Grade grade) {
        repository.delete(grade);
    }

    @Override
    public Grade findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Grade create(Grade grade) {
        return repository.save(grade);
    }

    @Override
    public Grade update(Grade g, Grade newGrade) {
        Grade gradeToUpdate = repository.findByName(g.getName());
        if (gradeToUpdate != null) {
            gradeToUpdate.setName(newGrade.getName());
            return repository.save(gradeToUpdate);
        }
        return null;
    }

    @Override
    public List<Grade> findAll() {
        return (List<Grade>) repository.findAll();
    }

    @Override
    public Grade findById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
