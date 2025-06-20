package com.aless00san.springboot.gunpladb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aless00san.springboot.gunpladb.entities.Gunpla;

public interface IGunplaRepository  extends CrudRepository<Gunpla, Long> {
    @Query("SELECT g FROM Gunpla g LEFT JOIN FETCH g.grade gr WHERE gr.name = :gradeName")
    List<Gunpla> findByGrade(String gradeName);
}
