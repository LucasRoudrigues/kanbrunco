package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.quadro.*;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.service.QuadroService; 
import com.example.kanbrunco.service.UsuarioService; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quadros")
public class QuadroController {

    private final QuadroService quadroService; 
    private final UsuarioService usuarioService; 
    public QuadroController(QuadroService quadroService, UsuarioService usuarioService) {
        this.quadroService = quadroService;
        this.usuarioService = usuarioService;
    }

    // Listar todos os quadros
    @GetMapping
    public String listarQuadros(Model model) {
        List<Quadro> quadros = quadroService.listarTodos(); 
        model.addAttribute("quadros", quadros);
        return "quadros/lista";
    }

    // Criar quadro normal
    @PostMapping("/criarNormal")
    public String criarQuadroNormal(@RequestParam String titulo,
                              @RequestParam Long proprietarioId) {
        Optional<Usuario> optionalProprietario = usuarioService.buscarPorId(proprietarioId);
        if (optionalProprietario.isPresent()) {
            Quadro quadro = new QuadroNormal() {};
            quadro.setTitulo(titulo);
            quadro.setProprietario(optionalProprietario.get());
            quadroService.salvarQuadroNormal(titulo,optionalProprietario.get()); 
        }
        return "redirect:/quadros";
    }

    // Criar quadro compartilhado
    @PostMapping("/criarCompartilhado")
    public String criarQuadroCompartilhado(@RequestParam String titulo,
                                           @RequestParam Long proprietarioId) {
        Optional<Usuario> optionalProprietario = usuarioService.buscarPorId(proprietarioId); 
        if (optionalProprietario.isPresent()) {
            QuadroCompartilhado quadro = new QuadroCompartilhado();
            quadro.setTitulo(titulo);
            quadro.setProprietario(optionalProprietario.get());
            quadroService.salvarQuadroCompartilhado(titulo,optionalProprietario.get()); 
        }
        return "redirect:/quadros";
    }

    // Atualizar quadro
    @PostMapping("/atualizar")
    public String atualizarQuadro(@RequestParam Long id,
                                  @RequestParam String titulo) {
        Optional<Quadro> optionalQuadro = quadroService.buscarPorId(id); 
        if (optionalQuadro.isPresent()) {
            Quadro quadro = optionalQuadro.get();
            quadro.setTitulo(titulo);
            quadroService.salvarQuadro(quadro); 
        }
        return "redirect:/quadros";
    }

    // Deletar quadro
    @PostMapping("/deletar")
    public String deletarQuadro(@RequestParam Long id) {
        quadroService.deletarQuadro(id); 
        return "redirect:/quadros";
    }

    // Compartilhamento de quadro com usuário
    @PostMapping("/compartilhar")
    public String compartilharQuadro(@RequestParam Long quadroId,
                                     @RequestParam Long usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(usuarioId); 
        if (optionalUsuario.isPresent()) {
            quadroService.compartilharQuadro(quadroId, optionalUsuario.get()); 
        }
        return "redirect:/quadros";

    }

    // Remover compartilhamento de quadro
    @PostMapping("/removerCompartilhamento")
    public String removerCompartilhamento(@RequestParam Long quadroId,
                                          @RequestParam Long usuarioId) {
        Optional<Quadro> optionalQuadro = quadroService.buscarPorId(quadroId);
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(usuarioId);
        if (optionalQuadro.isPresent() && optionalUsuario.isPresent() && optionalQuadro.get() instanceof QuadroCompartilhado) {
            QuadroCompartilhado quadroCompartilhado = (QuadroCompartilhado) optionalQuadro.get();
            quadroCompartilhado.removerCompartilhamento(optionalUsuario.get());
            quadroService.salvarQuadro(quadroCompartilhado);
        }
        return "redirect:/quadros";
    }
}

