package co.edu.javeriana.pw.back.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Forum {
       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;
    private String descripcion;
    private Boolean moderado;

    @OneToMany(mappedBy = "foro")
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Topic> temas;

    
    // List<User> moderadores;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getModerado() {
        return moderado;
    }

    public void setModerado(Boolean moderado) {
        this.moderado = moderado;
    }

    public List<Topic> getTemas() {
        return temas;
    }

    public void setTemas(List<Topic> temas) {
        this.temas = temas;
    }


    
}
