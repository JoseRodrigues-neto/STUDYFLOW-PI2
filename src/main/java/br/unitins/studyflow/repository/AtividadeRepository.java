package br.unitins.studyflow.repository;

import java.util.List;

import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.StatusAtividade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtividadeRepository implements PanacheRepository<Atividade>{

    public List<Atividade> findByTitulo(String titulo) {
        return find("SELECT a FROM Atividade a WHERE a.titulo like ?1", "%" + titulo + "%").list();
    }

    public List<Atividade> findByRoadmap(Long roadmapId, List<StatusAtividade> status) {
        if (status == null || status.isEmpty()) {
            return find("roadmap.id", roadmapId).list();
        }
        return find("roadmap.id = ?1 and status in ?2", roadmapId, status).list();
    }

    public List<Atividade> findByUsuarioAndRoadmapIsNull(Long usuarioId, List<StatusAtividade> status) {
        if (status == null || status.isEmpty()) {
            return find("usuario.id = ?1 and roadmap is null", usuarioId).list();
        }
        return find("usuario.id = ?1 and roadmap is null and status in ?2", usuarioId, status).list();
    }

    public List<Atividade> findAllByUsuarioAndStatusIn(Long usuarioId, List<StatusAtividade> status) {
        
        if (status == null || status.isEmpty()) {
            return find("usuario.id", usuarioId).list();
        }
        
        return find("usuario.id = ?1 and status in ?2", usuarioId, status).list();
    }
}