package com.example.kanbrunco.model.quadro;

import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.usuario.Usuario;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_quadro", discriminatorType = DiscriminatorType.STRING)
public abstract class Quadro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private Usuario proprietario;

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaTarefas> listas = new ArrayList<>();

    public Quadro() {}
    
  //MÉTODO PARA GERENCIAR A RELAÇÃO BIDIRECIONAL
    public void adicionarLista(ListaTarefas lista) {
        lista.setQuadro(this);
        this.listas.add(lista); }
 
    public Long getId() { 
    	return id; }
    public void setId(Long id) { 
    	this.id = id; }
    public String getTitulo() { 
    	return titulo; }
    public void setTitulo(String titulo) { 
    	this.titulo = titulo; }
    public Usuario getProprietario() { 
    	return proprietario; }
    public void setProprietario(Usuario proprietario) { 
    	this.proprietario = proprietario; }
    public List<ListaTarefas> getListas() { 
    	return listas; }
    public void setListas(List<ListaTarefas> listas) { 
    	this.listas = listas; }
}