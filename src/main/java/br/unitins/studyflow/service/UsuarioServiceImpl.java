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
import jakarta.ws.rs.core.Response;

import com.google.firebase.auth.FirebaseToken;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Override
    @Transactional
    public Response loginComGoogle(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            Usuario usuario = repository.find("uid", uid).firstResult();

            if (usuario != null) {
                // Usuário existe
                if (usuario.getPerfil() != null) {
                    // Perfil já definido, login completo
                    return Response.ok(Map.of("status", "COMPLETO")).build();
                } else {
                    // Perfil não definido
                    return Response.ok(Map.of("status", "SELECIONAR_PERFIL")).build();
                }
            } else {
                // Usuário não existe, criar novo
                Usuario novoUsuario = new Usuario();
                novoUsuario.setUid(uid);
                novoUsuario.setNome(decodedToken.getName());
                novoUsuario.setEmail(decodedToken.getEmail());
                // Perfil fica nulo até ser selecionado

                repository.persist(novoUsuario);

                return Response.ok(Map.of("status", "SELECIONAR_PERFIL")).build();
            }
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Token do Firebase inválido.", e);
        }
    }

    @Transactional
    @Override
    public UsuarioResponseDTO cadastrar(String uid, UsuarioRequestDTO usuarioDTO) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUid(uid);  
        novoUsuario.setNome(usuarioDTO.nome());
        novoUsuario.setEmail(usuarioDTO.email());
        novoUsuario.setDataNascimento(usuarioDTO.dataNascimento());
        
        if (usuarioDTO.perfil() != null) {
            novoUsuario.setPerfil(usuarioDTO.perfil());
            try {
                Map<String, Object> claims = new HashMap<>();
                claims.put("groups", List.of(usuarioDTO.perfil().name())); 
                FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException("Erro ao definir o perfil do usuário no Firebase.", e);
            }
        }

        repository.persist(novoUsuario);
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

        if (usuarioDTO.avatarUrl() != null) {
            usuario.setAvatarUrl(usuarioDTO.avatarUrl());
        }
        
        if (usuarioDTO.perfil() != null && !usuarioDTO.perfil().equals(usuario.getPerfil())) {
            usuario.setPerfil(usuarioDTO.perfil());
            try {
                Map<String, Object> claims = new HashMap<>();
                claims.put("groups", List.of(usuarioDTO.perfil().name()));
                FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException("Erro ao atualizar o perfil do usuário no Firebase.", e);
            }
        }
       
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

@Transactional
@Override
public void excluir(String uid) {  
    
    
    if (uid == null || uid.isBlank()) {
        throw new IllegalArgumentException("UID do usuário não pode ser nulo ou vazio para exclusão.");
    }

    try {  
        FirebaseAuth.getInstance().deleteUser(uid);

        long count = repository.delete("uid", uid);
        if (count == 0) {
            System.out.println("Aviso: Usuário com UID " + uid + " foi deletado do Firebase, mas não foi encontrado no banco de dados local.");
        }

    } catch (FirebaseAuthException e) {
        throw new RuntimeException("Erro ao deletar o usuário no Firebase. A operação foi cancelada.", e);
    }
}

@Transactional
    @Override
    public UsuarioResponseDTO atualizarAvatar(String uid, String urlDaImagem) {
        Usuario usuario = repository.find("uid", uid).firstResult();
        if (usuario == null) {
            return null; // Ou lançar exceção
        }

        // Simplesmente salva a URL completa do Firebase
        usuario.setAvatarUrl(urlDaImagem); 
        
        repository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);  
    }
}
