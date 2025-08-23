package com.example.kanbrunco.model.usuario;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("basico")
public class UsuarioBasico extends Usuario {
    
    public UsuarioBasico() {}
}