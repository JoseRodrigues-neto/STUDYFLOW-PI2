package br.unitins.studyflow.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roadmap")
public class Roadmap extends DefaultEntity {

    private String nome;
    private String descricao;
    private String nota;

     @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades;

     @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roadmap_usuario",  
               joinColumns = @JoinColumn(name = "roadmap_id"),  
               inverseJoinColumns = @JoinColumn(name = "usuario_id"))  
    private List<Usuario> usuarios;

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
    public List<Atividade> getAtividades() {
        return atividades;
    }
    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
   




}
