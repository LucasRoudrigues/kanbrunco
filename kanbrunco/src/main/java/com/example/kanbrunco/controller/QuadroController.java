package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.quadro.*;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.service.QuadroService;
import com.example.kanbrunco.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Listar todos os quadros do usuario
    @GetMapping
    public String listarQuadros(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        
        if (userId == null) {
            return "redirect:/";
        }

        Usuario usuario = usuarioService.buscarPorId(userId).orElseThrow();
        List<Quadro> quadros = quadroService.listarDoUsuario(usuario);

        List<Map<String, Object>> quadrosComInfo = new ArrayList<>();
        for (Quadro quadro : quadros) {
            Map<String, Object> quadroMap = new HashMap<>();
            quadroMap.put("quadro", quadro);
            quadroMap.put("isCompartilhado", quadro instanceof QuadroCompartilhado);
            quadrosComInfo.add(quadroMap);
        }

        model.addAttribute("quadros", quadrosComInfo);
        model.addAttribute("nivel", "quadro");
        return "quadros/lista";
    }

    // Criar quadro normal
    @PostMapping("/criarNormal")
    public String criarQuadroNormal(@RequestParam String titulo,
                                    @RequestParam Long proprietarioId) {
        Optional<Usuario> optionalProprietario = usuarioService.buscarPorId(proprietarioId);
        if (optionalProprietario.isPresent()) {
            quadroService.salvarQuadroNormal(titulo, optionalProprietario.get());
        }
        return "redirect:/quadros";
    }

    // Criar quadro compartilhado (Exclusivo para admins)
    @PostMapping("/criarCompartilhado")
    public String criarQuadroCompartilhado(@RequestParam String titulo,
                                           @RequestParam Long proprietarioId) {
        Optional<Usuario> optionalProprietario = usuarioService.buscarPorId(proprietarioId); 
        if (optionalProprietario.isPresent()) {
            quadroService.salvarQuadroCompartilhado(titulo, optionalProprietario.get()); 
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

    //Deletar quadro
    @PostMapping("/deletar")
    public String deletarQuadro(@RequestParam Long id) {
        quadroService.deletarQuadro(id);
        return "redirect:/quadros";
    }

    // Compartilhamento de quadro com usuário (Exclusivo para admins)
    @PostMapping("/compartilhar")
    public String compartilharQuadro(@RequestParam Long quadroId,
                                     @RequestParam Long usuarioId,
                                     HttpSession session, Model model) {
        String tipoUsuario = (String) session.getAttribute("currentUserTipo");
        if (!"UsuarioAdmin".equals(tipoUsuario)) {
            model.addAttribute("erro", "Apenas administradores podem compartilhar quadros.");
            return "quadros/lista"; 
        }
        
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