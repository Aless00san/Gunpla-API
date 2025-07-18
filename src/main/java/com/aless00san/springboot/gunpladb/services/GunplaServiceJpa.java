package com.aless00san.springboot.gunpladb.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aless00san.springboot.gunpladb.entities.Gunpla;
import com.aless00san.springboot.gunpladb.repositories.IGunplaRepository;

@Service
public class GunplaServiceJpa implements IGunplaService {

    @Autowired
    IGunplaRepository repository;

    @Override
    public List<Gunpla> findAll() {
        return (List<Gunpla>) repository.findAll();
    }

    @Override
    public Gunpla findById(Long id) {
        Optional<Gunpla> gunpla = repository.findById(id);
        return gunpla.orElse(null);
    }

    @Override
    public Gunpla save(Gunpla gunpla) {
        return repository.save(gunpla);
    }

    @Override
    public void delete(Gunpla gunpla) {
        repository.delete(gunpla);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Gunpla update(Long id, Gunpla gunpla) {
        Optional<Gunpla> gunplaToUpdate = repository.findById(id);
        if (gunplaToUpdate.isPresent()) {
            Gunpla dbGunpla = gunplaToUpdate.get();
            dbGunpla.setName(gunpla.getName());
            dbGunpla.setGrade(gunpla.getGrade());
            dbGunpla.setSeries(gunpla.getSeries());
            return repository.save(dbGunpla);
        }
        return gunplaToUpdate.orElse(null);
        
    }

    @Override
    public List<Gunpla> findByGrade(String grade) {
        return repository.findByGrade(grade);
    }

    @Override
    public List<Gunpla> findBySeries(String series) {
        return repository.findBySeries(series);
    }

    @Override
    public Page<Gunpla> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
