package br.unitins.studyflow.repository;

import java.util.List;

import br.unitins.studyflow.model.Roadmap;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoadmapRepository implements PanacheRepository<Roadmap>{

    public List<Roadmap> findByTitulo(String titulo) {
        return find("SELECT a FROM Roadmap a WHERE a.titulo like ?1", "%" + titulo + "%").list();
    }
}