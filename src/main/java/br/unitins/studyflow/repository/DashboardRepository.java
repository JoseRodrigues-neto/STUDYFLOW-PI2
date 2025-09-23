package br.unitins.studyflow.repository;

import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.StatusAtividade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DashboardRepository implements PanacheRepository<Atividade> {

   public long countByStatusAndUsuario(StatusAtividade status, Long usuarioId) {

        return count("status = ?1 and roadmap in (select r from Roadmap r join r.usuarios u where u.id = ?2)", status, usuarioId);
    }

    public long countByUsuario(Long usuarioId) {
        return count("roadmap in (select r from Roadmap r join r.usuarios u where u.id = ?1)", usuarioId);
    }
}
