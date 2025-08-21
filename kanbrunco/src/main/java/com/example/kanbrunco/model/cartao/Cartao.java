package com.example.kanbrunco.model.cartao;

import com.example.kanbrunco.model.comentario.Comentario;
import java.util.ArrayList;
import java.util.List;

public abstract class Cartao {

    private Long id;
    private String titulo;
    private String descricao;
    private List<Comentario> comentarios;
    private Long idquadro;

    public Cartao(){
    }

    public Cartao(Long id, String titulo, String descricao, Long idquadro) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.comentarios = new ArrayList<>();
        this.idquadro = idquadro;
    }

    // Método abstrato para polimorfismo, a ser implementado nas subclasses.
    public abstract void exibirDetalhes();

    // Método para adicionar um comentário ao cartão.
    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getIdQuadro() {
        return idquadro;
    }

    public void setIdQuadro(Long idquadro) {
        this.idquadro = idquadro;
    }
}
