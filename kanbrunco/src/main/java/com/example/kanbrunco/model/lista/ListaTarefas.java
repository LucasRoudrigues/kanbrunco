package com.example.kanbrunco.model.lista;

import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.model.quadro.Quadro;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ListaTarefas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "quadro_id")
    private Quadro quadro;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartoes = new ArrayList<>();

    public ListaTarefas() {}
 
    public Long getId() { 
    	return id; }
    public void setId(Long id) { 
    	this.id = id; }
    public String getTitulo() { 
    	return titulo; }
    public void setTitulo(String titulo) { 
    	this.titulo = titulo; }
    public Quadro getQuadro() { 
    	return quadro; }
    public void setQuadro(Quadro quadro) { 
    	this.quadro = quadro; }
    public List<Cartao> getCartoes() { 
    	return cartoes; }
    public void setCartoes(List<Cartao> cartoes) { 
    	this.cartoes = cartoes; }
}