package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.StatusAtividade;

public interface AtividadeService {
    Atividade create (AtividadeRequestDTO atividadeRequestDTO);

    Atividade update (Long id, AtividadeRequestDTO atividadeRequestDTO);

    Atividade findById(Long id);

    List<Atividade> findByTitulo(String titulo);

    List<Atividade> findByRoadmap(Long roadmapId, List<StatusAtividade> status);

    List<Atividade> findByUsuarioAndRoadmapIsNull(Long usuarioId, List<StatusAtividade> status);

    List<Atividade> findAllByUsuarioAndStatusIn(Long usuarioId, List<StatusAtividade> status);

    List<Atividade> findAll();

    void delete(long id);
}
