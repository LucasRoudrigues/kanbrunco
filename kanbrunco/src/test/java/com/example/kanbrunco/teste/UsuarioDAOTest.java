package com.example.kanbrunco.teste;

import com.example.kanbrunco.dao.UsuarioDAO;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioBasico;
import com.example.kanbrunco.model.usuario.UsuarioAdmin;
import java.util.List;

public class UsuarioDAOTest {

    public static void main(String[] args) {
        
        // 1. Instanciar o DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // 2. Criar alguns usuários para teste, usando a subclasse concreta
       /* 
        Usuario usuario1 = new UsuarioBasico();
        usuario1.setNome("Alice");
        usuario1.setEmail("alice1@email.com");

        Usuario usuario2 = new UsuarioBasico();
        usuario2.setNome("Bob");
        usuario2.setEmail("bob1@email.com");
        
        Usuario usuario3 = new UsuarioBasico();
        usuario3.setNome("Julio Cesar");
        usuario3.setEmail("julio_cleiton1@hotmail.com");
        */
        Usuario usuario1 = new UsuarioAdmin();
       	usuario1.setNome("Everlast silva Santos");
        usuario1.setEmail("everlast.legal@email.com");

        // 3. Salvar os usuários usando o DAO
        System.out.println("Salvando usuários...");
        usuarioDAO.salvar(usuario1);
        //usuarioDAO.salvar(usuario2);
       // usuarioDAO.salvar(usuario3);
        
        System.out.println("Usuários salvos com sucesso.");

        // 4. Carregar todos os usuários do arquivo
        System.out.println("Carregando usuários do arquivo...");
        List<Usuario> usuariosCarregados = usuarioDAO.carregarTodos();

        // 5. Verificar o resultado
        if (!usuariosCarregados.isEmpty()) {
            System.out.println("Usuários carregados:");
            for (Usuario u : usuariosCarregados) {
                System.out.println("ID: " + u.getId() + ", Nome: " + u.getNome() + ", Email: " + u.getEmail());
            }
        } else {
            System.out.println("Nenhum usuário foi carregado.");
        }
    }
}