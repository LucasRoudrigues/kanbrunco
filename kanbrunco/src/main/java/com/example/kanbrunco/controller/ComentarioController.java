package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.service.ComentarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;


    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }
    
    
    //Listar Comentários
    @GetMapping
    public String listarComentarios(Model model) {
        List<Comentario> comentarios = comentarioService.listarTodos();
        model.addAttribute("comentarios", comentarios);
        return "comentarios/lista";
    }
    
    //Criar comentário
    @PostMapping("/criar")
    public String criarComentario(@RequestParam Long cartaoId, @RequestParam String texto) {
        Comentario comentario = new Comentario();
        comentario.setTexto(texto);
        comentarioService.adicionarComentario(cartaoId, comentario);
        return "redirect:/comentarios";
    }

    @PostMapping("/atualizar")
    public String atualizarComentario(@RequestParam Long id, @RequestParam String texto) {
        comentarioService.buscarPorId(id).ifPresent(comentario -> {
            comentario.setTexto(texto);
            comentarioService.salvarComentario(comentario);
        });
        return "redirect:/comentarios";
    }

    @PostMapping("/deletar")
    public String deletarComentario(@RequestParam Long id) {
        comentarioService.deletarComentario(id);
        return "redirect:/comentarios";
    }
}
