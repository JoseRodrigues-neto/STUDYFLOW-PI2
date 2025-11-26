package br.unitins.studyflow.dto;

import java.time.LocalDate;

import br.unitins.studyflow.model.StatusAtividade;

public record AtividadeRequestDTO (
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataFim,
    StatusAtividade status,
    Long roadmapId,
    Long usuarioId
) {
    
}
