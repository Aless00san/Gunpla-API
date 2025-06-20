package com.aless00san.springboot.gunpladb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.aless00san.springboot.gunpladb.entities.Gunpla;
import com.aless00san.springboot.gunpladb.services.IGunplaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/gunpla")
public class GunplaController {

    @Autowired
    private IGunplaService gunplaService;

    @GetMapping("/list")
    public List<Gunpla> listAllGunplas() {
        return gunplaService.findAll();
    }

    @GetMapping("/{id}")
    public Gunpla findGunplaById(@PathVariable Long id) {
        return gunplaService.findById(id);
    }

    @GetMapping
    public List<Gunpla> getMethodName(@RequestParam String grade) {
        return gunplaService.findByGrade(grade);
    }
    
    

}
