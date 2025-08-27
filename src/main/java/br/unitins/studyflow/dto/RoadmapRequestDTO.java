package br.unitins.studyflow.dto;

public record RoadmapRequestDTO(
    String nome,
    String descricao,
    String nota,
    Long atividadeId
) {
    
}
