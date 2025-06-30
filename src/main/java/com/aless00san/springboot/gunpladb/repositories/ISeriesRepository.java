package com.aless00san.springboot.gunpladb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.aless00san.springboot.gunpladb.entities.Series;

public interface ISeriesRepository extends CrudRepository<Series, Long> {

    Series findByName(String name);

    void deleteById(Long id);
}
