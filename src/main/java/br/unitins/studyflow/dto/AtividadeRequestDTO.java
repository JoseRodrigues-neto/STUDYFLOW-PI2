package br.unitins.studyflow.dto;

import java.time.LocalDate;

public record AtividadeRequestDTO (
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataFim,
    Long roadmapId
) {
    
}
