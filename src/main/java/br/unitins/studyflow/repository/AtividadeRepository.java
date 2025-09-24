package br.unitins.studyflow.repository;

import java.util.List;

import br.unitins.studyflow.model.Atividade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtividadeRepository implements PanacheRepository<Atividade>{

    public List<Atividade> findByTitulo(String titulo) {
        return find("SELECT a FROM Atividade a WHERE a.titulo like ?1", "%" + titulo + "%").list();
    }

    public List<Atividade> findByRoadmap(Long roadmapId) {
        return find("roadmap.id", roadmapId).list();
    }
}