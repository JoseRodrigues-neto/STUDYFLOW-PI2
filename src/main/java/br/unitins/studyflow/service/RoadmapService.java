package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.RoadmapRequestDTO;
import br.unitins.studyflow.model.Roadmap;

public interface RoadmapService {
    Roadmap create (RoadmapRequestDTO dto);

    Roadmap update (Long id, RoadmapRequestDTO RoadmapRequestDTO);

    List<Roadmap> findByTitulo(String titulo);

    Roadmap findById(Long id);

    List<Roadmap> findAll();

    void delete(long id);
}
