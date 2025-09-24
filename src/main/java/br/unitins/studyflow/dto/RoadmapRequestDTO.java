package br.unitins.studyflow.dto;

public record RoadmapRequestDTO(
    String nome,
    String descricao,
    Long atividadeId
) {
    
}
