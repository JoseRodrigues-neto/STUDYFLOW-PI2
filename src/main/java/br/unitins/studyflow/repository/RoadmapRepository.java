package br.unitins.studyflow.repository;

import java.util.List;

import br.unitins.studyflow.model.Roadmap;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoadmapRepository implements PanacheRepository<Roadmap>{

    public List<Roadmap> findByNome(String nome) {
        return find("SELECT a FROM Roadmap a WHERE a.nome like ?1", "%" + nome + "%").list();
    }
}