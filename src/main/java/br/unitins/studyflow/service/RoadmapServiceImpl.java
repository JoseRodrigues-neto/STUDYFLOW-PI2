package br.unitins.studyflow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.studyflow.dto.RoadmapDTO;
import br.unitins.studyflow.dto.RoadmapRequestDTO;
import br.unitins.studyflow.model.Roadmap;
import br.unitins.studyflow.model.Usuario;
import br.unitins.studyflow.repository.RoadmapRepository;
import br.unitins.studyflow.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RoadmapServiceImpl implements RoadmapService {

    @Inject
    RoadmapRepository roadmapRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    @Override
    public List<Roadmap> findAll() {
        return roadmapRepository.findAll().list();
    }

    @Override
    public Roadmap findById(Long id) {
        Roadmap roadmap = roadmapRepository.findById(id);
        if (roadmap != null) {
            // Força o carregamento da descrição, caso seja lazy
            roadmap.getDescricao();
        }
        return roadmap;
    }

    @Override
    public List<RoadmapDTO> getRoadmaps() {
        String uid = jwt.getSubject();
        return roadmapRepository.listAll().stream()
                .map(RoadmapDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Roadmap create(RoadmapRequestDTO dto) {
        Roadmap roadmap = new Roadmap();
        roadmap.setTitulo(dto.titulo());
        roadmap.setDescricao(dto.descricao());

        // --- CORREÇÃO AQUI ---
        // Em vez de pegar o ID do DTO, pegamos o UID do Token
        String uid = jwt.getSubject();

        // Buscamos o usuário pelo UID do Firebase (que é garantido existir se o token
        // for válido)
        Usuario usuario = usuarioRepository.find("uid", uid).firstResult();

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado. Faça login novamente.");
        }

        roadmap.setUsuario(usuario);
        // ---------------------

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
