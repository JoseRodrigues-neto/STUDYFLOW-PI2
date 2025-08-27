package br.unitins.studyflow.service;

import br.unitins.studyflow.dto.AnotacaoRequestDTO;
import br.unitins.studyflow.model.Anotacao;
import br.unitins.studyflow.model.Atividade;
import br.unitins.studyflow.repository.AnotacaoRepository;
import br.unitins.studyflow.repository.AtividadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AnotacaoServiceImpl implements AnotacaoService{
    
    @Inject
    AnotacaoRepository anotacaoRepository;

    @Inject
    AtividadeRepository atividadeRepository;

    @Override
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
}
