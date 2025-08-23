package com.example.kanbrunco.service;

import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.repository.ListaTarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaTarefasService {

    @Autowired
    private ListaTarefasRepository listaTarefasRepository;

    public ListaTarefas salvarLista(ListaTarefas lista) {
        return listaTarefasRepository.save(lista);
    }

    public List<ListaTarefas> listarTodos() {
        return listaTarefasRepository.findAll();
    }

    public Optional<ListaTarefas> buscarPorId(Long id) {
        return listaTarefasRepository.findById(id);
    }

    public List<ListaTarefas> buscarPorQuadro(Quadro quadro) {
        return listaTarefasRepository.findByQuadro(quadro);
    }

    public void deletarLista(Long id) {
        listaTarefasRepository.deleteById(id);
    }
}