package br.unitins.studyflow.dto;

import java.util.List;
import br.unitins.studyflow.model.Roadmap;

public record RoadmapDTO(
        Long id,
        String titulo,
        String descricao,
        List<AtividadeDTO> atividades
) {
    public static RoadmapDTO valueOf(Roadmap roadmap) {
        List<AtividadeDTO> dtoList = (roadmap.getAtividades() == null)
                ? List.of() // Se for null, devolve lista vazia
                : roadmap.getAtividades()
                    .stream()
                    .map(AtividadeDTO::valueOf)
                    .toList();

        return new RoadmapDTO(
                roadmap.getId(),
                roadmap.getTitulo(),
                roadmap.getDescricao(),
                dtoList);
    }
}