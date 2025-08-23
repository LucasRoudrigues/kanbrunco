package com.example.kanbrunco.model.quadro;

import com.example.kanbrunco.model.usuario.Usuario;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("compartilhado")
public class QuadroCompartilhado extends Quadro {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "quadro_compartilhado_usuarios",
        joinColumns = @JoinColumn(name = "quadro_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosCompartilhados = new ArrayList<>();

    public QuadroCompartilhado() {}

    public void compartilharComUsuario(Usuario usuario) {
        if (!this.usuariosCompartilhados.contains(usuario)) {
            this.usuariosCompartilhados.add(usuario);
        }
    }
    
    public void removerCompartilhamento(Usuario usuario) {
        this.usuariosCompartilhados.remove(usuario);
    }
    
    public List<Usuario> getUsuariosCompartilhados() {
        return usuariosCompartilhados;
    }

    public void setUsuariosCompartilhados(List<Usuario> usuariosCompartilhados) {
        this.usuariosCompartilhados = usuariosCompartilhados;
    }
}