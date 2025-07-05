package com.aless00san.springboot.gunpladb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.aless00san.springboot.gunpladb.entities.Grade;
import com.aless00san.springboot.gunpladb.services.IGradeService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    IGradeService gradeService;

    // CrossOrigin for allowing the front-end to acces the API
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping("/list")
    public List<Grade> listAllGrades() {
        return gradeService.findAll();
    }

    @GetMapping("/{id}")
    public Grade findGradeById(@PathVariable Long id) {
        return gradeService.findById(id);
    }

    @GetMapping(params = {"name"})
    public Grade findGradeByName(@RequestParam String name) {
        return gradeService.findByName(name);
    }

    @PostMapping("/create")
    public Grade postMethodName(@RequestBody Grade grade) {
        return gradeService.create(grade);
    }
    
    @DeleteMapping("/{id}")
    public void deleteGradeById(@PathVariable Long id) {
        if (gradeService.findById(id) != null) {
            gradeService.delete(gradeService.findById(id));
        } else {
            throw new IllegalArgumentException("Grade not found");
        }
        
    }

}
