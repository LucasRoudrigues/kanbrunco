package com.example.kanbrunco.model.quadro;

import java.util.ArrayList;
import java.util.List;
import com.example.kanbrunco.model.usuario.Usuario;

public class QuadroCompartilhado extends Quadro {
    
    private List<Usuario> usuariosCompartilhados;

    public QuadroCompartilhado(Long id, String titulo, Usuario proprietario) {
        super(id, titulo, proprietario);
        this.usuariosCompartilhados = new ArrayList<>();
    }

    // Método para compartilhar o quadro com um usuário
    public void compartilharComUsuario(Usuario usuario) {
        // Lógica para adicionar o usuário à lista de compartilhamento
        if (!usuariosCompartilhados.contains(usuario)) {
            this.usuariosCompartilhados.add(usuario);
            System.out.println("Quadro '" + getTitulo() + "' compartilhado com o usuário: " + usuario.getNome());
        } else {
            System.out.println("O quadro já está compartilhado com este usuário.");
        }
    }
    
    // Método para remover o compartilhamento com um usuário
    public void removerCompartilhamento(Usuario usuario) {
        this.usuariosCompartilhados.remove(usuario);
        System.out.println("Compartilhamento do quadro '" + getTitulo() + "' removido para o usuário: " + usuario.getNome());
    }

    // Getter
    public List<Usuario> getUsuariosCompartilhados() {
        return usuariosCompartilhados;
    }
}
