package com.example.kanbrunco.teste;

import com.example.kanbrunco.dao.QuadroDAO;
import com.example.kanbrunco.dao.ListaDAO;
import com.example.kanbrunco.dao.UsuarioDAO;
import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioAdmin;
import com.example.kanbrunco.model.usuario.UsuarioBasico;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOTest {

    public static void main(String[] args) {
        
        // 1. Instanciar os DAOs
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        QuadroDAO quadroDAO = new QuadroDAO(usuarioDAO);
        ListaDAO listaDAO = new ListaDAO();

        // 2. Criar e salvar um novo usuário
        System.out.println("--- Teste de Cadastro de Usuário ---");
        Usuario usuario = new UsuarioAdmin();
        usuario.setNome("Everlast silva Santos");
        usuario.setEmail("everlast.legal@email.com");
        usuarioDAO.salvar(usuario);
        System.out.println("Usuário salvo: ID=" + usuario.getId() + ", Nome=" + usuario.getNome() + "\n");
        
        // 3. Criar e salvar um novo quadro
        System.out.println("--- Teste de Cadastro de Quadro ---");
        Quadro quadro = new Quadro();
        quadro.setTitulo("Quadro de Tarefas de khalil");
        quadro.setProprietario(usuario); // Associa o quadro ao usuário
        quadroDAO.salvar(quadro);
        System.out.println("Quadro salvo: ID=" + quadro.getId() + ", Título=" + quadro.getTitulo() + "\n");

        // 4. Criar e salvar uma nova lista de tarefas
        System.out.println("--- Teste de Cadastro de Lista ---");
        ListaTarefas lista = new ListaTarefas();
        lista.setTitulo("Tarefas em andamentor");
        lista.setQuadroId(quadro.getId()); // Associa a lista ao ID do quadro
        listaDAO.salvar(lista);
        System.out.println("Lista salva: ID=" + lista.getId() + ", Título=" + lista.getTitulo() + "\n");
        
        // 5. Verificar a persistência (lendo os arquivos)
        System.out.println("--- Verificando a Persistência ---");
        
        // Carregar e exibir todos os usuários
        List<Usuario> usuariosCarregados = usuarioDAO.carregarTodos();
        System.out.println("Usuários carregados do arquivo:");
        for (Usuario u : usuariosCarregados) {
            System.out.println("- Usuário ID: " + u.getId() + ", Nome: " + u.getNome() + ", Email: " + u.getEmail());
        }
        
        // Carregar e exibir todos os quadros
        List<Quadro> quadrosCarregados = quadroDAO.carregarTodos();
        System.out.println("Quadros carregados do arquivo:");
        for (Quadro q : quadrosCarregados) {
            System.out.println("- Quadro ID: " + q.getId() + ", Título: " + q.getTitulo() + ", Proprietário ID: " + q.getProprietario().getId());
        }

        // Carregar e exibir todas as listas
        List<ListaTarefas> listasCarregadas = listaDAO.carregarTodos();
        System.out.println("\nListas carregadas do arquivo:");
        for (ListaTarefas l : listasCarregadas) {
            System.out.println("- Lista ID: " + l.getId() + ", Título: " + l.getTitulo() + ", Quadro ID: " + l.getQuadroId());
        }
    }
}