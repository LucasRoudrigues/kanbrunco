package com.example.kanbrunco.model.comentario;

import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kabrunco.model.cartao.Cartao;
import java.time.LocalDateTime;

// Esta classe representa um comentário em um cartão.
public class Comentario {

    private Long id;
    private String texto;
    private Usuario autor;
    private LocalDateTime dataCriacao;
    private Cartao cartao;

    public Comentario(Long id,String texto, Usuario autor, Cartao cartao) {
        this.id=id;
        this.cartao= cartao;
        this.texto = texto;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now(); // Define a data de criação automaticamente.
    }

    public Long getId(){
        return id;
    }
    public String getTexto() {
        return texto;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Cartao getCartao(){
        return cartao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
