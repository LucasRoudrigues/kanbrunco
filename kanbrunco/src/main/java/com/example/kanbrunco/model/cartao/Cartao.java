package com.example.kanbrunco.model.cartao;

import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.model.lista.ListaTarefas;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cartao", discriminatorType = DiscriminatorType.STRING)
public abstract class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "lista_id")
    private ListaTarefas lista;

    public Cartao() {}

    public void adicionarComentario(Comentario comentario) {
        comentario.setCartao(this);
        this.comentarios.add(comentario);
    }

    public void removerComentario(Comentario comentario) {
        if (this.comentarios.contains(comentario)) {
            this.comentarios.remove(comentario);
            comentario.setCartao(null);
        }
    }

    public Long getId() { 
    	return id; }
    public void setId(Long id) { 
    	this.id = id; }
    public String getTitulo() { 
    	return titulo; }
    public void setTitulo(String titulo) { 
    	this.titulo = titulo; }
    public String getDescricao() { 
    	return descricao; }
    public void setDescricao(String descricao) { 
    	this.descricao = descricao; }
    public List<Comentario> getComentarios() { 
    	return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { 
    	this.comentarios = comentarios; }
    public ListaTarefas getLista() { 
    	return lista; }
    public void setLista(ListaTarefas lista) { 
    	this.lista = lista; }
}