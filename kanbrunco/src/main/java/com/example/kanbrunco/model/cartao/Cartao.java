package com.example.kanbrunco.model.cartao;

import com.example.kanbrunco.model.comentario.Comentario;
import java.util.ArrayList;
import java.util.List;

public abstract class Cartao {

    private String id;
    private String titulo;
    private String descricao;
    private List<Comentario> comentarios;

    public Cartao(String id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.comentarios = new ArrayList<>();
    }

    // Método abstrato para polimorfismo, a ser implementado nas subclasses.
    public abstract void exibirDetalhes();

    // Método para adicionar um comentário ao cartão.
    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }
}
