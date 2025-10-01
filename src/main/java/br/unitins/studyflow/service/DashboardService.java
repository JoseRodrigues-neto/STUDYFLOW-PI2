package br.unitins.studyflow.service;

import br.unitins.studyflow.dto.DashboardDTO;

public interface DashboardService {

    DashboardDTO getDashboardData(Long usuarioId);
}
