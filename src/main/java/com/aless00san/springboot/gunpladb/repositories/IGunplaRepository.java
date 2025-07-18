package com.aless00san.springboot.gunpladb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aless00san.springboot.gunpladb.entities.Gunpla;

public interface IGunplaRepository  extends JpaRepository<Gunpla, Long> {
    @Query("SELECT g FROM Gunpla g LEFT JOIN FETCH g.grade gr WHERE gr.name = :gradeName")
    List<Gunpla> findByGrade(String gradeName);

    @Query("SELECT g FROM Gunpla g LEFT JOIN FETCH g.series gs WHERE gs.name = :seriesName")
    List<Gunpla> findBySeries(String seriesName);
    
}
