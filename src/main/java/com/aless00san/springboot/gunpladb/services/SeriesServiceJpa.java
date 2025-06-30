package com.aless00san.springboot.gunpladb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aless00san.springboot.gunpladb.entities.Series;
import com.aless00san.springboot.gunpladb.repositories.ISeriesRepository;

@Service
public class SeriesServiceJpa implements ISeriesService {

    @Autowired
    ISeriesRepository repository;

    @Override
    public List<Series> findAll() {
        return (List<Series>) repository.findAll();
    }

    @Override
    public Series findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Series save(Series series) {
        return repository.save(series);
    }

    @Override
    public Series findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void delete(Series series) {
        repository.delete(series);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Series update(Series oldSeries, Series newSeries) {
        Series dbSeries = repository.findById(oldSeries.getId()).orElse(null);

        if (dbSeries != null) {
            dbSeries.setName(newSeries.getName());
            dbSeries.setSource(newSeries.getSource());
            return repository.save(dbSeries);
        }
        return null;
        
    }

}
