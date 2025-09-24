package br.unitins.studyflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;
import br.unitins.studyflow.model.Usuario;
import br.unitins.studyflow.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Transactional
    @Override
    public UsuarioResponseDTO cadastrar(String uid, UsuarioRequestDTO usuarioDTO) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUid(uid);  
        novoUsuario.setNome(usuarioDTO.nome());
        novoUsuario.setEmail(usuarioDTO.email());
        novoUsuario.setDataNascimento(usuarioDTO.dataNascimento());
        novoUsuario.setPerfil(usuarioDTO.perfil());

        repository.persist(novoUsuario);

            try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("groups", List.of(usuarioDTO.perfil().name())); 

            FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
        } catch (FirebaseAuthException e) {
          
            throw new RuntimeException("Erro ao definir o perfil do usuário no Firebase.", e);
        }
        return UsuarioResponseDTO.valueOf(novoUsuario);
    }

    @Override
    public List<UsuarioResponseDTO> buscarTodos() {
        return repository.listAll().stream()
                .map(UsuarioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO buscarPorId(String uid) {
        Usuario usuario = repository.find("uid", uid).firstResult();
        if (usuario == null) {
            return null;
        }
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Transactional
    @Override
    public UsuarioResponseDTO atualizar(String uid, UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = repository.find("uid", uid).firstResult();
        if (usuario == null) {
            return null;
        }

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setDataNascimento(usuarioDTO.dataNascimento());
        usuario.setPerfil(usuarioDTO.perfil());

       
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Transactional
    @Override
    public boolean excluir(String uid) {
        return repository.delete("uid", uid) > 0;
    }
}
