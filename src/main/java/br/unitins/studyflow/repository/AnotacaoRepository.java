package br.unitins.studyflow.repository;

import java.util.List;

import br.unitins.studyflow.model.Anotacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnotacaoRepository implements PanacheRepository<Anotacao>{
    
    public List<Anotacao> findByAtividade(Long id) {
        return find("atividade.id = ?1", id).list();
    }

}
