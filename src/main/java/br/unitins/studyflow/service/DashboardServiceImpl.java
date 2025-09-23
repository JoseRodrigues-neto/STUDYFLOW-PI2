package br.unitins.studyflow.service;

import java.util.HashMap;
import java.util.Map;

import br.unitins.studyflow.dto.DashboardDTO;
import br.unitins.studyflow.model.StatusAtividade;
import br.unitins.studyflow.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DashboardServiceImpl implements DashboardService {

    @Inject
    DashboardRepository dashboardRepository;

    @Override
    public DashboardDTO getDashboardData(Long usuarioId) {
        
           long concluidas = dashboardRepository.countByStatusAndUsuario(StatusAtividade.CONCLUIDO, usuarioId);
        long pendentes = dashboardRepository.countByStatusAndUsuario(StatusAtividade.PENDENTE, usuarioId);
        long emAndamento = dashboardRepository.countByStatusAndUsuario(StatusAtividade.EM_ANDAMENTO, usuarioId);
        long total = dashboardRepository.countByUsuario(usuarioId);

        Map<String, Long> statusCounts = new HashMap<>();
        statusCounts.put("Concluído", concluidas);
        statusCounts.put("Pendente", pendentes);
        statusCounts.put("Em Andamento", emAndamento);
        
        return new DashboardDTO(
            total,
            concluidas,
            pendentes,
            emAndamento,
            statusCounts
        );
    }
}
