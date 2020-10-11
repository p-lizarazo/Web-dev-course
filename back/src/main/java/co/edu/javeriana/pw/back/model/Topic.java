package co.edu.javeriana.pw.back.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topic {
      
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date fechaPublicacion;
    private String titulo;
    private String contenido;

    @ManyToOne
    private Forum foro;

    @OneToMany(mappedBy = "tema")
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Comment> comentarios;
    
    @OneToMany(mappedBy = "tema")
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<TopicVote> votos;


    //private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Forum getForo() {
        return foro;
    }

    public void setForo(Forum foro) {
        this.foro = foro;
    }

    public List<Comment> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comment> comentarios) {
        this.comentarios = comentarios;
    }

    public List<TopicVote> getVotos() {
        return votos;
    }

    public void setVotos(List<TopicVote> votos) {
        this.votos = votos;
    }


    
}
