package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.service.ComentarioService;
import com.example.kanbrunco.service.CartaoService;
import com.example.kanbrunco.model.cartao.Cartao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final CartaoService cartaoService;

    public ComentarioController(ComentarioService comentarioService, CartaoService cartaoService) {
        this.comentarioService = comentarioService;
        this.cartaoService = cartaoService;
    }
    
    // Listar comentarios
    @GetMapping
    public String listarComentarios(Model model) {
        List<Comentario> comentarios = comentarioService.listarTodos();
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("nivel", "comentario");
        return "comentarios/lista";
    }

    // Listar comentários de um cartão específico
    @GetMapping("/cartao/{cartaoId}")
    public String listarComentariosPorCartao(@PathVariable Long cartaoId, Model model) {
        Optional<Cartao> cartaoOpt = cartaoService.buscarPorId(cartaoId);
        if (cartaoOpt.isPresent()) {
            Cartao cartao = cartaoOpt.get();
            List<Comentario> comentarios = comentarioService.buscarPorCartao(cartao);
            model.addAttribute("comentarios", comentarios);
            model.addAttribute("cartao", cartao);
            model.addAttribute("nivel", "comentario");
            return "comentarios/lista";
        }
        return "redirect:/cartoes";
    }
    
    // Cria comentario em um cartão
    @PostMapping("/criar")
    public String criarComentario(@RequestParam Long cartaoId, @RequestParam String texto) {
        Comentario comentario = new Comentario();
        comentario.setTexto(texto);
        comentarioService.adicionarComentario(cartaoId, comentario);
        return "redirect:/comentarios/cartao/" + cartaoId;
    }

    // Atualiza o comentario de um cartão
    @PostMapping("/atualizar")
    public String atualizarComentario(@RequestParam Long id, @RequestParam String texto) {
        // Encontra o comentario para obter o ID do cartão antes de atualizar
        Optional<Comentario> comentarioOpt = comentarioService.buscarPorId(id);
        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentario.setTexto(texto);
            comentarioService.salvarComentario(comentario);
            return "redirect:/comentarios/cartao/" + comentario.getCartao().getId();
        }
        return "redirect:/";
    }

    // Deleta o comentario de um cartão
    @PostMapping("/deletar")
    public String deletarComentario(@RequestParam Long id) {
        // Encontra o comentário para obter o ID do cartão antes de deletar
        Optional<Comentario> comentarioOpt = comentarioService.buscarPorId(id);
        if (comentarioOpt.isPresent()) {
            Long cartaoId = comentarioOpt.get().getCartao().getId();
            comentarioService.deletarComentario(id);
            return "redirect:/comentarios/cartao/" + cartaoId;
        }
        return "redirect:/";
    }
}