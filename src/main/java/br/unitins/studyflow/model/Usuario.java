package br.unitins.studyflow.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario  {
 
     @Id
    private String uid;


    // adicionar id novamente 
  
    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

 
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
   
    @Column(name = "tipo_perfil")
    private String tipoPerfil;

    public Usuario() {
    }

    public Usuario(String nome, String email,LocalDate dataNacimento, String tipoPerfil) {
        this.nome = nome;
       this.dataNascimento = dataNacimento;
        this.email = email;
        this.tipoPerfil = tipoPerfil;
    } 
 
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               ", tipoPerfil='" + tipoPerfil + '\'' +
               '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

  
}
