package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.service.ListaTarefasService;
import com.example.kanbrunco.service.QuadroService;

import jakarta.servlet.http.HttpSession;

import com.example.kanbrunco.service.CartaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/listas")
public class ListaTarefasController {

    private final ListaTarefasService listaService;
    private final QuadroService quadroService;
    private final CartaoService cartaoService;

    public ListaTarefasController(ListaTarefasService listaService,
                                  QuadroService quadroService,
                                  CartaoService cartaoService) {
        this.listaService = listaService;
        this.quadroService = quadroService;
        this.cartaoService = cartaoService;
    }

    // Listar todas as listas de um quadro
    @GetMapping("/quadro/{quadroId}")
    public String listarListasPorQuadro(@PathVariable Long quadroId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            return "redirect:/";
        }

        Optional<Quadro> quadroOpt = quadroService.buscarPorId(quadroId);
        if (quadroOpt.isPresent()) {
            Quadro quadro = quadroOpt.get();
            List<ListaTarefas> listas = listaService.buscarPorQuadro(quadro);
            model.addAttribute("listas", listas);
            model.addAttribute("quadro", quadro);
            model.addAttribute("nivel", "lista");
            return "listas/lista";
        }
        return "redirect:/quadros";
    }

    // Criar lista e associar a um quadro
    @PostMapping("/criar")
    public String criarLista(@RequestParam String titulo,
                             @RequestParam Long quadroId) {
        Optional<Quadro> optionalQuadro = quadroService.buscarPorId(quadroId);
        if (optionalQuadro.isPresent()) {
            Quadro quadro = optionalQuadro.get();
            ListaTarefas lista = new ListaTarefas();
            lista.setTitulo(titulo);
            lista.setQuadro(quadro);
            listaService.salvarLista(lista);
            quadro.adicionarLista(lista);
            quadroService.salvarQuadro(quadro);
        }
        return "redirect:/listas/quadro/" + quadroId;
    }

    // Atualiza a lista de um quadro
    @PostMapping("/atualizar")
    public String atualizarLista(@RequestParam Long id,
                                 @RequestParam String titulo, @RequestParam Long quadroId) {
        Optional<ListaTarefas> optionalLista = listaService.buscarPorId(id);
        if (optionalLista.isPresent()) {
            ListaTarefas lista = optionalLista.get();
            lista.setTitulo(titulo);
            listaService.salvarLista(lista);
        }
        return "redirect:/listas/quadro/" + quadroId;
    }

    // Deleta a lista de um quadro
    @PostMapping("/deletar")
    public String deletarLista(@RequestParam Long id, @RequestParam Long quadroId) {
        listaService.deletarLista(id);
        return "redirect:/listas/quadro/" + quadroId;
    }

    // Adiciona um cartão a uma lista
    @PostMapping("/adicionarCartao")
    public String adicionarCartao(@RequestParam Long listaId,
                                  @RequestParam Long cartaoId) {
        Optional<ListaTarefas> optionalLista = listaService.buscarPorId(listaId);
        Optional<Cartao> optionalCartao = cartaoService.buscarPorId(cartaoId);
        if (optionalLista.isPresent() && optionalCartao.isPresent()) {
            ListaTarefas lista = optionalLista.get();
            Cartao cartao = optionalCartao.get();
            lista.adicionarCartao(cartao);
            listaService.salvarLista(lista);
            cartaoService.salvarCartao(cartao);
        }
        return "redirect:/listas";
    }
}