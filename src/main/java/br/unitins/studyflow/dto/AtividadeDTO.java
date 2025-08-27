package br.unitins.studyflow.dto;

import java.time.LocalDate;

import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.StatusAtividade;

public record AtividadeDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        StatusAtividade status) {

    public static AtividadeDTO valueOf(Atividade atividade) {
        return new AtividadeDTO(
                atividade.getId(),
                atividade.getTitulo(),
                atividade.getDescricao(),
                atividade.getDataInicio(),
                atividade.getDataFim(),
                atividade.getStatus());
    }
}