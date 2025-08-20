package br.unitins.studyflow.repository;

import br.unitins.studyflow.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class  UsuarioRepository implements PanacheRepository<Usuario> {

}
