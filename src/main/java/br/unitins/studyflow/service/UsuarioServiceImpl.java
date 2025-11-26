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
                            // Usuário não existe pelo UID, vamos verificar pelo email para evitar duplicatas
                            // e permitir a vinculação de contas (ex: usuário se cadastrou com email/senha antes)
                            Usuario usuarioPorEmail = repository.find("email", decodedToken.getEmail()).firstResult();
                
                            if (usuarioPorEmail != null) {
                                // Usuário com este email já existe. Vamos apenas atualizar o UID dele.
                                // Isso vincula a conta do Google à conta existente de email/senha.
                                usuarioPorEmail.setUid(uid);
                                repository.persist(usuarioPorEmail); // 'persist' aqui funciona como um 'merge'
                
                                // Agora, verifica o perfil como se o tivéssemos encontrado pelo UID
                                if (usuarioPorEmail.getPerfil() != null) {
                                    return Response.ok(Map.of("status", "COMPLETO")).build();
                                } else {
                                    return Response.ok(Map.of("status", "SELECIONAR_PERFIL")).build();
                                }
                            } else {
                                // Usuário realmente não existe. Criar novo.
                                Usuario novoUsuario = new Usuario();
                                novoUsuario.setUid(uid);
                                novoUsuario.setNome(decodedToken.getName());
                                novoUsuario.setEmail(decodedToken.getEmail());
                                // Perfil fica nulo até ser selecionado
                
                                repository.persist(novoUsuario);
                
                                return Response.ok(Map.of("status", "SELECIONAR_PERFIL")).build();
                            }            }
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
        // Verifica se o email foi alterado e se o novo email já existe
        if (usuarioDTO.email() != null && !usuarioDTO.email().equals(usuario.getEmail())) {
            Usuario existenteComNovoEmail = repository.find("email", usuarioDTO.email()).firstResult();
            if (existenteComNovoEmail != null && !existenteComNovoEmail.getUid().equals(uid)) {
                // Encontrou outro usuário com o novo email
                throw new RuntimeException("Erro: O email '" + usuarioDTO.email() + "' já está em uso por outro usuário.");
            }
            usuario.setEmail(usuarioDTO.email());
        } else {
            // Mantém o email original se não for fornecido um novo
            usuario.setEmail(usuario.getEmail());
        }
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

        // 1. Tenta deletar do banco de dados local
        long count = repository.delete("uid", uid);

        if (count == 0) {
            System.out.println("Aviso: Usuário com UID " + uid + " não encontrado no banco de dados local. Verificando o Firebase...");
            // Se não encontrou localmente, podemos tentar apagar do Firebase apenas para garantir,
            // mas não vamos gerar erro se já não existir lá.
            try {
                FirebaseAuth.getInstance().deleteUser(uid);
                System.out.println("Usuário com UID " + uid + " deletado do Firebase, mas não estava no banco de dados local.");
            } catch (FirebaseAuthException e) {
                if (e.getErrorCode().equals("user-not-found")) {
                    System.out.println("Usuário com UID " + uid + " não encontrado no Firebase, nenhuma ação necessária.");
                } else {
                    System.err.println("Erro inesperado ao deletar o usuário no Firebase (UID: " + uid + ") que não estava no banco de dados local. Erro: " + e.getMessage());
                    throw new RuntimeException("Erro ao tentar garantir a exclusão no Firebase. " + e.getMessage(), e);
                }
            }
        } else { // Usuário encontrado e deletado localmente
            try {
                FirebaseAuth.getInstance().deleteUser(uid); // Delete from Firebase
                System.out.println("Usuário com UID " + uid + " deletado com sucesso do banco de dados local e do Firebase.");
            } catch (FirebaseAuthException e) {
                // Se o Firebase falhar, a transação JPA será automaticamente revertida (rollback)
                // devido ao @Transactional, mantendo o usuário no banco de dados local também.
                System.err.println("Erro ao deletar o usuário no Firebase (UID: " + uid + "). A transação local será revertida. Erro: " + e.getMessage());
                throw new RuntimeException("Erro ao deletar o usuário no Firebase. A operação de exclusão completa foi abortada.", e);
            }
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
