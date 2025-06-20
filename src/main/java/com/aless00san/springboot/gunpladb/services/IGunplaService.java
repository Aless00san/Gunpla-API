package com.aless00san.springboot.gunpladb.services;

import java.util.List;

import com.aless00san.springboot.gunpladb.entities.Gunpla;

public interface IGunplaService {

    List<Gunpla> findAll();

    Gunpla findById(Long id);

    Gunpla save(Gunpla gunpla);

    void delete(Gunpla gunpla);

    void deleteById(Long id);

    Gunpla update(Long id, Gunpla gunpla);

    List<Gunpla> findByGrade(String grade);

    List<Gunpla> findBySeries(String series);
}
