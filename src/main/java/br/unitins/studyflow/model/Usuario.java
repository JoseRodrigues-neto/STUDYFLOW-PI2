package br.unitins.studyflow.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends DefaultEntity  {

    @Column(unique = true, nullable = false, length = 128)// id do usuario do firebase
    private String uid;
  
    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

 
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Enumerated(EnumType.STRING) 
    @Column(name = "tipo_perfil")
    private Perfil perfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Roadmap> roadmaps;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;


    public Usuario() {
    }

    public Usuario(String nome, String email,LocalDate dataNacimento, Perfil perfil) {
        this.nome = nome;
       this.dataNascimento = dataNacimento;
        this.email = email;
        this.perfil = perfil;
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


    public Perfil getPerfil() {
        return perfil;
    }
   

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               ", perfil='" + perfil + '\'' +
               '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Roadmap> getRoadmaps() {
        return roadmaps;
    }

    public void setRoadmaps(List<Roadmap> roadmaps) {
        this.roadmaps = roadmaps;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

  
}
