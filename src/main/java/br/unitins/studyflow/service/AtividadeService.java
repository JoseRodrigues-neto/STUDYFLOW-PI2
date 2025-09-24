package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.model.Atividade;

public interface AtividadeService {
    Atividade create (AtividadeRequestDTO atividadeRequestDTO);

    Atividade update (Long id, AtividadeRequestDTO atividadeRequestDTO);

    List<Atividade> findByTitulo(String titulo);

    List<Atividade> findByRoadmap(Long roadmapId);

    List<Atividade> findAll();

    void delete(long id);
}
