package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.RoadmapDTO;
import br.unitins.studyflow.dto.RoadmapRequestDTO;
import br.unitins.studyflow.model.Roadmap;

public interface RoadmapService {
    Roadmap create (RoadmapRequestDTO dto);

    Roadmap update (Long id, RoadmapRequestDTO RoadmapRequestDTO);

    Roadmap findById(Long id);

    List<RoadmapDTO> getRoadmaps();

    List<Roadmap> findAll();

    void delete(long id);
}
