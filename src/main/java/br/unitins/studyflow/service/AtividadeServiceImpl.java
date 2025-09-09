package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.model.StatusAtividade;
import br.unitins.studyflow.repository.AtividadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AtividadeServiceImpl implements AtividadeService {

    @Inject
    AtividadeRepository atividadeRepository;

    @Override
    public List<Atividade> findAll() {
        return atividadeRepository.findAll().list();
    }

    @Override
    public List<Atividade> findByTitulo(String titulo) {
        return atividadeRepository.findByTitulo(titulo);
    }

    @Override
    @Transactional
    public Atividade create(AtividadeRequestDTO atividadeRequestDTO) {

        Atividade atividade = new Atividade();
        atividade.setTitulo(atividadeRequestDTO.titulo());
        atividade.setDescricao(atividadeRequestDTO.descricao());
        atividade.setDataInicio(atividadeRequestDTO.dataInicio());
        atividade.setDataFim(atividadeRequestDTO.dataFim());
        atividade.setStatus(StatusAtividade.PENDENTE);

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

        return atividade;
    }

    @Override
    @Transactional
    public void delete(long id) {
        atividadeRepository.deleteById(id);
    }

}
