package com.aless00san.springboot.gunpladb.services;

import java.util.List;

import com.aless00san.springboot.gunpladb.entities.Series;

public interface ISeriesService {

    List<Series> findAll();

    Series findById(Long id);

    Series save(Series series);

    Series findByName(String name);

    void delete(Series series);

    void deleteById(Long id);

    Series update(Series oldSeries, Series newSeries);
}
