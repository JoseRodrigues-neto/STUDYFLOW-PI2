package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.RoadmapRequestDTO;
import br.unitins.studyflow.model.Roadmap;

public interface RoadmapService {
    Roadmap create (RoadmapRequestDTO RoadmapRequestDTO);

    Roadmap update (Long id, RoadmapRequestDTO RoadmapRequestDTO);

    List<Roadmap> findByNome(String nome);

    List<Roadmap> findById(Long roadmapId);

    List<Roadmap> findAll();

    void delete(long id);
}
