package com.aless00san.springboot.gunpladb.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Gunpla {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Grade grade;

    @ManyToOne
    private Series series;

    private Date lastKnownReprint;


    //TODO implement pbandai
    // @Transient
    // @JsonIgnore
    // private boolean pBandai = false;

    public Gunpla() {}

    public Gunpla(Long id, String name, Grade grade, Series series, Date lastKnownReprint) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.series = series;
        this.lastKnownReprint = lastKnownReprint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Date getLastKnownReprint() {
        return lastKnownReprint;
    }

    public void setLastKnownReprint(Date lastKnownReprint) {
        this.lastKnownReprint = lastKnownReprint;
    }
}
