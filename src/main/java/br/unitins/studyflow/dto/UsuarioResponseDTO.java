package br.unitins.studyflow.dto;

import java.time.LocalDate;

import br.unitins.studyflow.model.Perfil;
import br.unitins.studyflow.model.Usuario;

public record UsuarioResponseDTO(
    String uid,
    long id,
    String nome,
    String email,
    LocalDate dataNascimento,
    Perfil perfil,
    String avatarUrl
) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getUid(),
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getDataNascimento(),
            usuario.getPerfil(),
            usuario.getAvatarUrl() 
        );
    }
}