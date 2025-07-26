package com.aless00san.springboot.gunpladb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aless00san.springboot.gunpladb.entities.Series;
import com.aless00san.springboot.gunpladb.services.ISeriesService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    @Autowired
    ISeriesService seriesService;

    @GetMapping("/list")
    public List<Series> listAll() {
        return seriesService.findAll();
    }

    @GetMapping("/{id}")
    public Series findSeriesById(@PathVariable Long id) {
        return seriesService.findById(id);
    }

    @GetMapping(params = { "name" })
    public Series findSeriesByName(@RequestParam String name) {
        return seriesService.findByName(name);
    }

    @PostMapping("/create")
    public Series postMethodName(@RequestBody Series series) {
        return seriesService.save(series);
    }

}
