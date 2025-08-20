package br.unitins.studyflow.model;

public enum StatusAtividade {
    PENDENTE (1, "Pendente"),
    EM_ANDAMENTO (2, "Em andamento"),
    CONCLUIDO (3, "Concluído");

    private final Integer id;
    private final String descricao;

    private StatusAtividade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAtividade valueOf(Integer id) {
        if (id == null)
            return null;
        for (StatusAtividade status : StatusAtividade.values()) {
            if (status.getId().equals(id))
                return status;
        }
        throw new IllegalArgumentException("Id inválido");
    }
}
