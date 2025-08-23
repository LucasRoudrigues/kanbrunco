package com.example.kanbrunco.model.usuario;

import com.example.kanbrunco.model.quadro.Quadro;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quadro> quadros = new ArrayList<>();

    public Usuario() {}

    public Long getId() { 
    	return id; }
    public void setId(Long id) { 
    	this.id = id; }
    public String getNome() { 
    	return nome; }
    public void setNome(String nome) { 
    	this.nome = nome; }
    public String getEmail() { 
    	return email; }
    public void setEmail(String email) { 
    	this.email = email; }
    public List<Quadro> getQuadros() { 
    	return quadros; }
    public void setQuadros(List<Quadro> quadros) { 
    	this.quadros = quadros; }
}