package com.example.kanbrunco.model.cartao;

import com.example.kanbrunco.model.comentario.Comentario;
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
    
    // Mapeamento para a lista de comentarios e conexão de um cartão possuindo varios comentarios
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

    @Column(name = "id_quadro")
    private Long idQuadro;

    public Cartao() {
    }

    // Método para adicionar um comentário ao cartão
    public void adicionarComentario(Comentario comentario) {
        comentario.setCartao(this);
        this.comentarios.add(comentario);
    }

    // Método para remover um comentário do cartão
    public void removerComentario(Comentario comentario) {
        if (this.comentarios.contains(comentario)) {
            this.comentarios.remove(comentario);
            comentario.setCartao(null);
        }
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

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Long getIdQuadro() {
        return idQuadro;
    }

    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
    }
}
