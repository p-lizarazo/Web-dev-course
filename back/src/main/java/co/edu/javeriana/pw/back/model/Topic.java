package co.edu.javeriana.pw.back.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Topic {
      
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date fechaPublicacion;
    
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private String ownerUsername;

    @Column(nullable = false)
    private Boolean aprobado;

    @ManyToOne
    private Forum foro;

    @OneToMany(mappedBy = "tema")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Comment> comentarios;
    
    @OneToMany(mappedBy = "tema")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<TopicVote> votos;

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

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }  
}
