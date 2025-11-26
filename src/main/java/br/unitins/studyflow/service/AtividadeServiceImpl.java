package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.Roadmap;
import br.unitins.studyflow.model.StatusAtividade;
import br.unitins.studyflow.repository.AtividadeRepository;
import br.unitins.studyflow.repository.RoadmapRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import br.unitins.studyflow.model.Usuario;
import br.unitins.studyflow.repository.UsuarioRepository;

@ApplicationScoped
public class AtividadeServiceImpl implements AtividadeService {

    @Inject
    AtividadeRepository atividadeRepository;

    @Inject
    RoadmapRepository roadmapRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public List<Atividade> findAll() {
        return atividadeRepository.findAll().list();
    }

    @Override
    public List<Atividade> findByTitulo(String titulo) {
        return atividadeRepository.findByTitulo(titulo);
    }

    @Override
    public List<Atividade> findByRoadmap(Long roadmapId, List<StatusAtividade> status) {
        return atividadeRepository.findByRoadmap(roadmapId, status);
    }

    @Override
    public List<Atividade> findByUsuarioAndRoadmapIsNull(Long usuarioId, List<StatusAtividade> status) {
        return atividadeRepository.findByUsuarioAndRoadmapIsNull(usuarioId, status);
    }

    @Override
    public List<Atividade> findAllByUsuarioAndStatusIn(Long usuarioId, List<StatusAtividade> status) {
        return atividadeRepository.findAllByUsuarioAndStatusIn(usuarioId, status);
    }

    @Override
    @Transactional
    public Atividade create(AtividadeRequestDTO atividadeRequestDTO) {

        Atividade atividade = new Atividade();
        atividade.setTitulo(atividadeRequestDTO.titulo());
        atividade.setDescricao(atividadeRequestDTO.descricao());
        atividade.setDataInicio(atividadeRequestDTO.dataInicio());
        atividade.setDataFim(atividadeRequestDTO.dataFim());
        atividade.setStatus(atividadeRequestDTO.status());

        // A atividade deve sempre ter um usuário associado.
        if (atividadeRequestDTO.usuarioId() == null) {
            throw new RuntimeException("O ID do usuário é obrigatório para criar uma atividade.");
        }
        Usuario usuario = usuarioRepository.findById(atividadeRequestDTO.usuarioId());
        if (usuario == null) {
            throw new RuntimeException("Usuário com ID " + atividadeRequestDTO.usuarioId() + " não encontrado.");
        }
        atividade.setUsuario(usuario);

        // Se um roadmapId for fornecido, associa o roadmap.
        if (atividadeRequestDTO.roadmapId() != null) {
            Roadmap roadmap = roadmapRepository.findById(atividadeRequestDTO.roadmapId());
            if (roadmap == null) {
                // Considerar lançar uma exceção mais específica, como NotFoundException
                throw new RuntimeException("Roadmap com ID " + atividadeRequestDTO.roadmapId() + " não encontrado.");
            }
            atividade.setRoadmap(roadmap);
        }

        atividadeRepository.persist(atividade);
        return atividade;
    }

    @Override
    @Transactional
    public Atividade update(Long id, AtividadeRequestDTO atividadeRequestDTO) {

        Atividade atividade = atividadeRepository.findById(id);
        atividade.setTitulo(atividadeRequestDTO.titulo());
        atividade.setDescricao(atividadeRequestDTO.descricao());
        atividade.setDataInicio(atividadeRequestDTO.dataInicio());
        atividade.setDataFim(atividadeRequestDTO.dataFim());
        atividade.setStatus(atividadeRequestDTO.status());

        // Se um roadmapId for fornecido, associa o roadmap.
        if (atividadeRequestDTO.roadmapId() != null) {
            Roadmap roadmap = roadmapRepository.findById(atividadeRequestDTO.roadmapId());
            if (roadmap == null) {
                // Considerar lançar uma exceção mais específica, como NotFoundException
                throw new RuntimeException("Roadmap com ID " + atividadeRequestDTO.roadmapId() + " não encontrado.");
            }
            atividade.setRoadmap(roadmap);
        } else {
            atividade.setRoadmap(null);
        }

        return atividade;
    }

    @Override
    public Atividade findById(Long id) {
        return atividadeRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        atividadeRepository.deleteById(id);
    }

}
