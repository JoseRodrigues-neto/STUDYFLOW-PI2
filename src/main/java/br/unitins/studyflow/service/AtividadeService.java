package br.unitins.studyflow.service;

import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.model.Atividade;

public interface AtividadeService {
    Atividade create (AtividadeRequestDTO atividadeRequestDTO);
}
