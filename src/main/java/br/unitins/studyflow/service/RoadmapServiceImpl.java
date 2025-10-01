package br.unitins.studyflow.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.studyflow.dto.RoadmapRequestDTO;
import br.unitins.studyflow.model.Roadmap;
import br.unitins.studyflow.model.Usuario;
import br.unitins.studyflow.repository.RoadmapRepository;
import br.unitins.studyflow.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RoadmapServiceImpl implements RoadmapService{
    
    @Inject
    RoadmapRepository roadmapRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public List<Roadmap> findAll() {
        return roadmapRepository.findAll().list();
    }

    @Override
    public Roadmap findById(Long id) {
        return roadmapRepository.findById(id);
    }

    @Override
    @Transactional
    public Roadmap create(RoadmapRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Roadmap roadmap = new Roadmap();
        roadmap.setTitulo(dto.titulo());
        roadmap.setDescricao(dto.descricao());
        roadmap.setUsuario(usuario);
        roadmap.setAtividades(new ArrayList<>());

        roadmapRepository.persist(roadmap);

        return roadmap;
    }

    @Override
    @Transactional
    public Roadmap update(Long id, RoadmapRequestDTO dto) {
        Roadmap roadmap = roadmapRepository.findById(id);
        if (roadmap == null) {
            throw new IllegalArgumentException("Roadmap não encontrado");
        }
    // pensar en outra solucao se possivel 
               roadmap.getAtividades().size();

        roadmap.setTitulo(dto.titulo());
        roadmap.setDescricao(dto.descricao());

        return roadmap;
    }

    @Override
    @Transactional
    public void delete(long id) {
        roadmapRepository.deleteById(id);
    }

    
}
