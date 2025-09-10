package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.AnotacaoRequestDTO;
import br.unitins.studyflow.model.Anotacao;
import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.repository.AnotacaoRepository;
import br.unitins.studyflow.repository.AtividadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AnotacaoServiceImpl implements AnotacaoService {

    @Inject
    AnotacaoRepository anotacaoRepository;

    @Inject
    AtividadeRepository atividadeRepository;

    @Override
    public List<Anotacao> findAll() {
        return anotacaoRepository.findAll().list();
    }

    @Override
    public List<Anotacao> findByAtividade(Long id) {
        return anotacaoRepository.findByAtividade(id);
    }

    @Override
    public Anotacao findById(Long id) {
        return anotacaoRepository.findById(id);
    }

    @Override
    @Transactional
    public Anotacao create(AnotacaoRequestDTO dto) {
        Atividade atividade = atividadeRepository.findById(dto.atividadeId());
        if (atividade == null) {
            throw new IllegalArgumentException("Atividade não encontrada com id: " + dto.atividadeId());
        }

        Anotacao anotacao = new Anotacao();
        anotacao.setConteudo(dto.conteudo());
        anotacao.setAtividade(atividade);

        anotacaoRepository.persist(anotacao);
        return anotacao;
    }

    @Override
    @Transactional
    public Anotacao update(Long id, AnotacaoRequestDTO dto) {
        Anotacao anotacao = anotacaoRepository.findById(id);
        if (anotacao == null) {
            throw new IllegalArgumentException("Anotação não encontrada com id: " + id);
        }

        Atividade atividade = atividadeRepository.findById(dto.atividadeId());
        if (atividade == null) {
            throw new IllegalArgumentException("Atividade não encontrada com id: " + dto.atividadeId());
        }

        anotacao.setConteudo(dto.conteudo());
        anotacao.setAtividade(atividade);

        return anotacao;
    }

    @Override
    @Transactional
    public void delete(long id) {
        anotacaoRepository.deleteById(id);
    }
}
