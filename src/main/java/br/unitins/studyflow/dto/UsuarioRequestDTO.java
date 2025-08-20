package br.unitins.studyflow.dto;

import java.time.LocalDate;

public record  UsuarioRequestDTO (
       String nome,
    String email,
    String senha,
    LocalDate dataNascimento,
    String tipoPerfil
) {
}