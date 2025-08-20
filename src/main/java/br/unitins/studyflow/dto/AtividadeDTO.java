package br.unitins.studyflow.dto;

import java.time.LocalDate;

public record AtividadeDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        String status
    ) {

}
