package br.unitins.studyflow.dto;

import java.util.List;
import java.util.Map;

public record DashboardDTO(
   Long total,
    Long concluidas,
    Long pendentes,
    Long emAndamento,
    Long algumaCoisa1,
    Map<String, Long> statusCounts,
    Integer algumaCoisa2,
    List<RoadmapResumoDTO> roadmaps,
    Map<String, Long> outraCoisa,
    Map<String, Long> outraCoisa2,
    UsuarioDashboardDTO usuario
) {

public record RoadmapResumoDTO(
    String titulo,
    Long total,
    Long concluidas
) {}

public record UsuarioDashboardDTO(
    String nome,
    String email,
    String avatarUrl
) {}


}
