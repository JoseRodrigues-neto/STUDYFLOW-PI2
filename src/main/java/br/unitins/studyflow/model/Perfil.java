package br.unitins.studyflow.model;

public enum Perfil {
    ALUNO(1, "Aluno"),
    PROFESSOR(2, "Professor");

    private int id;
    private String label;

    Perfil(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

   
    public static Perfil valueOf(int id) {
        for (Perfil perfil : values()) {
            if (perfil.getId() == id) {
                return perfil;
            }
        }
        throw new IllegalArgumentException("Id de Perfil inválido.");
    }
}