package br.unitins.studyflow.dto;

import java.time.LocalDate;

public record  UsuarioRequestDTO (
       String nome,
    String email,
    LocalDate dataNascimento,
    String tipoPerfil
) {
}