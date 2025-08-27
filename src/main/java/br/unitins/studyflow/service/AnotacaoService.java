package br.unitins.studyflow.service;

import br.unitins.studyflow.dto.AnotacaoRequestDTO;
import br.unitins.studyflow.model.Anotacao;

public interface AnotacaoService {
    Anotacao create (AnotacaoRequestDTO dto);
}
