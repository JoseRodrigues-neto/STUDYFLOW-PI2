package br.unitins.studyflow.dto;

import br.unitins.studyflow.model.Anotacao;

public record AnotacaoDTO (
    Long id,
    String conteudo,
    Long idAtividade
) {
    public static AnotacaoDTO valueOf(Anotacao anotacao) {
        return new AnotacaoDTO(
            anotacao.getId(),
            anotacao.getConteudo(),
            anotacao.getAtividade()!= null ? anotacao.getAtividade().getId() : null);
    }
}
