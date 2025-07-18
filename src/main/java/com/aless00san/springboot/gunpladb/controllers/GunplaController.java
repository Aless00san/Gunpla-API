package com.aless00san.springboot.gunpladb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aless00san.springboot.gunpladb.entities.Gunpla;
import com.aless00san.springboot.gunpladb.services.IGunplaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/gunpla")
public class GunplaController {

    @Autowired
    private IGunplaService gunplaService;

    // CrossOrigin for allowing the front-end to acces the API
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/list")
    public List<Gunpla> listAllGunplas() {
        return gunplaService.findAll();
    }

    @GetMapping("/{id}")
    public Gunpla findGunplaById(@PathVariable Long id) {
        return gunplaService.findById(id);
    }

    @GetMapping(params = { "grade" })
    public List<Gunpla> findByGrade(@RequestParam String grade) {
        return gunplaService.findByGrade(grade);
    }

    @GetMapping(params = { "series" })
    public List<Gunpla> findBySeries(@RequestParam String series) {
        return gunplaService.findBySeries(series);
    }

    @DeleteMapping("/{id}")
    public void deleteGunplaById(@PathVariable Long id) {
        gunplaService.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public Gunpla createGunpla(@RequestBody Gunpla gunpla) {
        Gunpla entity = gunplaService.save(gunpla);
        return entity;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/{id}")
    public Gunpla updateGunpla(@PathVariable Long id, @RequestBody Gunpla gunpla) {
        Gunpla entity = gunplaService.update(id, gunpla);
        return entity;
    }

@GetMapping(value = "/list", params = {"page", "size"})
    public Page<Gunpla> getPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gunplaService.findAll(pageable);
    }

}
