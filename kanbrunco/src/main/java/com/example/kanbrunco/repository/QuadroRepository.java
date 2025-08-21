package com.example.kanbrunco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kanbrunco.model.quadro.Quadro;

@Repository
public interface QuadroRepository extends JpaRepository<Quadro, Long> {

}
