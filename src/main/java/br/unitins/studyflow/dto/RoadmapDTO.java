package br.unitins.studyflow.dto;

public record RoadmapDTO(
    Long id,
    String nome,
    String descricao,
    String nota,
    AtividadeDTO atividade
) {
	
}
