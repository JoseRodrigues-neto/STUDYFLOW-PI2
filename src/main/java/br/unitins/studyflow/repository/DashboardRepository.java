package br.unitins.studyflow.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.TypedQuery;

import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.Roadmap;
import br.unitins.studyflow.model.StatusAtividade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
 
public class DashboardRepository implements PanacheRepository<Atividade> {

   public long countByStatusAndUsuario(StatusAtividade status, Long usuarioId) {

        return count("status = ?1 and roadmap in (select r from Roadmap r join r.usuario u where u.id = ?2)", status, usuarioId);
    }

    public long countByUsuario(Long usuarioId) {
        return count("roadmap in (select r from Roadmap r join r.usuario u where u.id = ?1)", usuarioId);
    }

 
  @Inject
    EntityManager em;
    public List<Atividade> buscarAtividadesFiltradas(Long usuarioId, Long roadmapId,
                                                     LocalDate from, LocalDate to,
                                                     String order) {

        StringBuilder sql = new StringBuilder("""
            SELECT a FROM Atividade a
            WHERE a.roadmap.usuario.id = :usuarioId
        """);

        if (roadmapId != null) {
            sql.append(" AND a.roadmap.id = :roadmapId");
        }

        if (from != null) {
            sql.append(" AND a.dataInicio >= :fromDate");
        }

        if (to != null) {
            sql.append(" AND a.dataInicio <= :toDate");
        }

        // ordenação
        switch (order != null ? order : "") {
            case "date_asc" -> sql.append(" ORDER BY a.dataInicio ASC");
            case "date_desc" -> sql.append(" ORDER BY a.dataInicio DESC");
            case "status" -> sql.append(" ORDER BY a.status ASC");
            case "titulo" -> sql.append(" ORDER BY a.titulo ASC");
            default -> sql.append(" ORDER BY a.id DESC");
        }

        TypedQuery<Atividade> query = (TypedQuery<Atividade>) em.createQuery(sql.toString(), Atividade.class);
        query.setParameter("usuarioId", usuarioId);

        if (roadmapId != null) query.setParameter("roadmapId", roadmapId);
        if (from != null) query.setParameter("fromDate", from);
        if (to != null) query.setParameter("toDate", to);

        return query.getResultList();
    }

    public List<Roadmap> findRoadmapsByUsuario(Long usuarioId) {
    return em.createQuery(
        "SELECT r FROM Roadmap r WHERE r.usuario.id = :id",
        Roadmap.class
    )
    .setParameter("id", usuarioId)
    .getResultList();
}
public List<Atividade> findAtividadesByUsuario(Long usuarioId) {
    return em.createQuery(
            "SELECT a FROM Atividade a WHERE a.roadmap.usuario.id = :id",
            Atividade.class
        )
        .setParameter("id", usuarioId)
        .getResultList();
}


}


