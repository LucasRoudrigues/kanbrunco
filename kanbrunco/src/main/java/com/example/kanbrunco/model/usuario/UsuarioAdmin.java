package com.example.kanbrunco.model.usuario;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class UsuarioAdmin extends Usuario {

    public UsuarioAdmin() {}
}