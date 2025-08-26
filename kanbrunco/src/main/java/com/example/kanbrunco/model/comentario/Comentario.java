package com.example.kanbrunco.model.comentario;

import com.example.kanbrunco.model.cartao.Cartao;
import jakarta.persistence.*;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    public Comentario() {}

    // Metodos gets e sets da classe comentario
    public Long getId() { 
    	return id; }
    public void setId(Long id) { 
    	this.id = id; }
    public String getTexto() { 
    	return texto; }
    public void setTexto(String texto) { 
    	this.texto = texto; }
    public Cartao getCartao() { 
    	return cartao; }
    public void setCartao(Cartao cartao) { 
    	this.cartao = cartao; }
}