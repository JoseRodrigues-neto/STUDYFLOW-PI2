package br.unitins.studyflow.repository;

import java.util.List;

import br.unitins.studyflow.model.Anotacao;
import br.unitins.studyflow.model.Atividade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnotacaoRepository implements PanacheRepository<Anotacao>{
    
    public List<Anotacao> findByAtividade(Atividade atividade) {
        return list("atividade", atividade);
    }

    public List<Anotacao> findByAtividadeId(Long atividadeId) {
        return list("atividade.id", atividadeId);
    }
}
