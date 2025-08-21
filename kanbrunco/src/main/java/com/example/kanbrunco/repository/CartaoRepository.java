package com.example.kanbrunco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kanbrunco.model.cartao.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

}