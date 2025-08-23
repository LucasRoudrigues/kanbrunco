package com.example.kanbrunco.teste;

import com.example.kanbrunco.KanbruncoApplication;
import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.model.cartao.CartaoSimples;
import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.quadro.QuadroNormal;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioBasico;
import com.example.kanbrunco.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = KanbruncoApplication.class)
public class TesteIntegracaoCompleto {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private QuadroRepository quadroRepository;
    @Autowired
    private ListaTarefasRepository listaTarefasRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Test
    @Transactional
    public void testarFluxoDePersistenciaCompleto() {
        System.out.println("--- INICIANDO TESTE DE INTEGRAÇÃO COMPLETO ---");

        // 1. Criar e persistir um usuário
        Usuario usuario = new UsuarioBasico();
        usuario.setNome("Usuário de Teste");
        usuario.setEmail("teste.completo@email.com");
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        assertNotNull(usuarioSalvo.getId(), "Usuário deve ser persistido e ter um ID.");
        System.out.println("1. Usuário criado e salvo: " + usuarioSalvo.getId());

        // 2. Criar e persistir um quadro
        Quadro quadro = new QuadroNormal();
        quadro.setTitulo("Quadro Principal");
        quadro.setProprietario(usuarioSalvo);
        Quadro quadroSalvo = quadroRepository.save(quadro);
        assertNotNull(quadroSalvo.getId(), "Quadro deve ser persistido e ter um ID.");
        assertEquals(usuarioSalvo.getId(), quadroSalvo.getProprietario().getId(), "Quadro deve estar associado ao usuário.");
        System.out.println("2. Quadro criado e salvo: " + quadroSalvo.getId());

        // 3. Criar e persistir uma lista de tarefas
        ListaTarefas lista = new ListaTarefas();
        lista.setTitulo("Tarefas a Fazer");
        quadroSalvo.adicionarLista(lista);
        ListaTarefas listaSalva = listaTarefasRepository.save(lista);
        assertNotNull(listaSalva.getId(), "Lista deve ser persistida e ter um ID.");
        assertEquals(quadroSalvo.getId(), listaSalva.getQuadro().getId(), "Lista deve estar associada ao quadro.");
        System.out.println("3. Lista de Tarefas criada e salva: " + listaSalva.getId());

        // 4. Criar e persistir um cartão
        Cartao novoCartao = new CartaoSimples();
        novoCartao.setTitulo("Implementar feature X");
        novoCartao.setDescricao("Descrição da feature");
        listaSalva.adicionarCartao(novoCartao); 
        Cartao cartaoSalvo = cartaoRepository.save(novoCartao);
        assertNotNull(cartaoSalvo.getId(), "Cartão deve ser persistido e ter um ID.");
        assertEquals(listaSalva.getId(), cartaoSalvo.getLista().getId(), "Cartão deve estar associado à lista.");
        System.out.println("4. Cartão criado e salvo: " + cartaoSalvo.getId());

        // 5. Criar e persistir um comentário
        Comentario novoComentario = new Comentario();
        novoComentario.setTexto("Comentário inicial sobre a tarefa.");
        cartaoSalvo.adicionarComentario(novoComentario); 
        Comentario comentarioSalvo = comentarioRepository.save(novoComentario);
        assertNotNull(comentarioSalvo.getId(), "Comentário deve ser persistido e ter um ID.");
        assertEquals(cartaoSalvo.getId(), comentarioSalvo.getCartao().getId(), "Comentário deve estar associado ao cartão.");
        System.out.println("5. Comentário criado e salvo: " + comentarioSalvo.getId());

        // 6. Testar se as relações foram salvas corretamente (verificando o fluxo reverso)
        System.out.println("\n--- Verificando Relacionamentos ---");
        
        Cartao cartaoRecuperado = cartaoRepository.findById(cartaoSalvo.getId()).orElseThrow();
        assertEquals(comentarioSalvo.getId(), cartaoRecuperado.getComentarios().get(0).getId(), "Comentário deve ser encontrado no cartão.");
        System.out.println("Relacionamento 'Cartão -> Comentário' OK.");

        ListaTarefas listaRecuperada = listaTarefasRepository.findById(listaSalva.getId()).orElseThrow();
        assertEquals(cartaoSalvo.getId(), listaRecuperada.getCartoes().get(0).getId(), "Cartão deve ser encontrado na lista.");
        System.out.println("Relacionamento 'Lista -> Cartão' OK.");
        
        Quadro quadroRecuperado = quadroRepository.findById(quadroSalvo.getId()).orElseThrow();
        assertEquals(listaSalva.getId(), quadroRecuperado.getListas().get(0).getId(), "Lista deve ser encontrada no quadro.");
        System.out.println("Relacionamento 'Quadro -> Lista' OK.");

        // 7. Testar a deleção em cascata (CASCADE.ALL)
        System.out.println("\n--- Testando a Deleção em Cascata ---");
        quadroRepository.deleteById(quadroSalvo.getId());
        
        assertFalse(quadroRepository.existsById(quadroSalvo.getId()), "Quadro deve ser deletado.");
        assertFalse(listaTarefasRepository.existsById(listaSalva.getId()), "Lista deve ser deletada.");
        assertFalse(cartaoRepository.existsById(cartaoSalvo.getId()), "Cartão deve ser deletado.");
        assertFalse(comentarioRepository.existsById(comentarioSalvo.getId()), "Comentário deve ser deletado.");
        
        System.out.println("Deleção em cascata concluída com sucesso!");
    }
}