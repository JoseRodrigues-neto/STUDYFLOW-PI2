package br.unitins.studyflow.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.unitins.studyflow.dto.DashboardDTO;
import br.unitins.studyflow.dto.DashboardDTO.RoadmapResumoDTO;
import br.unitins.studyflow.dto.DashboardDTO.UsuarioDashboardDTO;
import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.Roadmap;
import br.unitins.studyflow.model.StatusAtividade;
import br.unitins.studyflow.model.Usuario;
import br.unitins.studyflow.repository.DashboardRepository;
import br.unitins.studyflow.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DashboardServiceImpl implements DashboardService {

    @Inject
    DashboardRepository dashboardRepository;

    @Inject
    UsuarioRepository usuarioRepository;

  @Override
    public DashboardDTO getDashboardData(Long usuarioId) {

        long concluidas = dashboardRepository.countByStatusAndUsuario(StatusAtividade.CONCLUIDO, usuarioId);
        long pendentes = dashboardRepository.countByStatusAndUsuario(StatusAtividade.PENDENTE, usuarioId);
        long emAndamento = dashboardRepository.countByStatusAndUsuario(StatusAtividade.EM_ANDAMENTO, usuarioId);
        long total = dashboardRepository.countByUsuario(usuarioId);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        Map<String, Long> statusCounts = Map.of(
            "Concluído", concluidas,
            "Pendente", pendentes,
            "Em Andamento", emAndamento
        );

        List<Roadmap> listaRoadmaps = dashboardRepository.findRoadmapsByUsuario(usuarioId);

        List<RoadmapResumoDTO> roadmaps = listaRoadmaps.stream()
                .map(r -> new RoadmapResumoDTO(
                        r.getTitulo(),
                        (long) r.getAtividades().size(),
                        r.getAtividades().stream()
                                .filter(a -> a.getStatus() == StatusAtividade.CONCLUIDO)
                                .count()
                ))
                .toList();

        List<Atividade> atividades = dashboardRepository.findAtividadesByUsuario(usuarioId);

        

        Map<String, Long> atividadesCriadasPorMes =
                atividades.stream()
                        .collect(Collectors.groupingBy(
                                a -> a.getDataInicio().format(fmt),
                                Collectors.counting()
                        ));

Map<String, Long> atividadesConcluidasPorMes =
    atividades.stream()
        .filter(a -> a.getStatus() == StatusAtividade.CONCLUIDO)
        .filter(a -> a.getDataFim() != null)
        .collect(Collectors.groupingBy(
            a -> a.getDataFim().format(fmt),
            Collectors.counting()
        ));
        Usuario u = usuarioRepository.findById(usuarioId);

        UsuarioDashboardDTO usuarioInfo = new UsuarioDashboardDTO(
                u.getNome(),
                u.getEmail(),
                u.getAvatarUrl()
        );
        return new DashboardDTO(
                total,
                concluidas,
                pendentes,
                emAndamento,
                0L,
                statusCounts,
                0,
                roadmaps,
                atividadesCriadasPorMes,
                atividadesConcluidasPorMes,
                usuarioInfo
        );
    }
}