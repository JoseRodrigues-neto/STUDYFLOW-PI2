package br.unitins.studyflow.repository;

import br.unitins.studyflow.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    // Método especial para encontrar um usuário pelo UID do Firebase
    public Usuario findByUid(String uid) {
        return find("uid", uid).firstResult();
    }
}