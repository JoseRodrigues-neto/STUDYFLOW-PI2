package br.unitins.studyflow.dto;

import java.time.LocalDate;

import br.unitins.studyflow.model.Perfil;

public record  UsuarioRequestDTO (
       String nome,
    String email,
    LocalDate dataNascimento,
    Perfil perfil
) {
}