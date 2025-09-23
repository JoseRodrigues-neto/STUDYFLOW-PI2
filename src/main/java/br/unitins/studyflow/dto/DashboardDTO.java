package br.unitins.studyflow.dto;

import java.util.Map;

public record DashboardDTO(
    long totalAtividades,
    long atividadesConcluidas,
    long atividadesPendentes,
    long atividadesEmAndamento,
    Map<String, Long> statusCounts
) {
}

