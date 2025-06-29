package com.aless00san.springboot.gunpladb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aless00san.springboot.gunpladb.entities.Grade;
import com.aless00san.springboot.gunpladb.services.IGradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    IGradeService gradeService;

    @RequestMapping("/list")
    public List<Grade> listAllGrades() {
        return gradeService.findAll();
    }

    @GetMapping("/{id}")
    public Grade findGradeById(@PathVariable Long id) {
        return gradeService.findById(id);
    }
    

}
