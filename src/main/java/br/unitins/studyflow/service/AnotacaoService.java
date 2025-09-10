package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.AnotacaoRequestDTO;
import br.unitins.studyflow.model.Anotacao;

public interface AnotacaoService {
    Anotacao create (AnotacaoRequestDTO dto);

    Anotacao update (Long id, AnotacaoRequestDTO anotacaoRequestDTO);

    Anotacao findById(Long id);

    List<Anotacao> findByAtividade(Long id);

    List<Anotacao> findAll();

    void delete(long id);
}
