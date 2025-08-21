package com.example.kanbrunco.model.usuario;
import com.example.kanbrunco.model.quadro.Quadro;
import java.util.ArrayList;
import java.util.List;

// Atende ao requisito de herança.
public class UsuarioBasico extends Usuario {
    
    private List<Quadro> quadros;

    public UsuarioBasico(String id, String nome, String email) {
        super(id, nome, email);
        this.quadros = new ArrayList<>();
    }

    @Override
    public void criarQuadro() {
        // Lógica para criar um novo quadro.
        // Um usuário básico pode criar e administrar seus próprios quadros.
        System.out.println("Usuário básico " + getNome() + " criou um novo quadro.");
    }

    @Override
    public void gerenciarUsuarios() {
        // Usuário básico não tem permissão para gerenciar outros usuários.
        System.out.println("Permissão negada. Apenas administradores podem gerenciar usuários.");
    }
    
    public void addQuadro(Quadro quadro) {
        this.quadros.add(quadro);
    }

    public List<Quadro> getQuadros() {
        return quadros;
    }
}
