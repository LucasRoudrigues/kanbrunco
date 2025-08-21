package com.example.kanbrunco.model.comentario;

import com.example.kanbrunco.model.usuario.Usuario;
import java.time.LocalDateTime;

// Esta classe representa um comentário em um cartão.
public class Comentario {

    private String texto;
    private Usuario autor;
    private LocalDateTime dataCriacao;

    public Comentario(String texto, Usuario autor) {
        this.texto = texto;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now(); // Define a data de criação automaticamente.
    }
    public String getTexto() {
        return texto;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
