package br.unitins.studyflow.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roadmap")
public class Roadmap extends DefaultEntity {

    private String nome;
    private String descricao;
    private String nota;
    
    @OneToMany
    private List<Atividade> atividade;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getNota() {
        return nota;
    }
    public void setNota(String nota) {
        this.nota = nota;
    }
    public List<Atividade> getAtividade() {
        return atividade;
    }
    public void setAtividade(List<Atividade> atividade) {
        this.atividade = atividade;
    }





}
