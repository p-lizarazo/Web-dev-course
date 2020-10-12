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
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private Date fechaPublicacion;

    @Column(nullable = false)
    private Boolean aprobado;

    @Column(nullable = false)
    private String ownerUsername;
    
    @ManyToOne
    private Topic tema;

    @OneToMany(mappedBy = "padre")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Comment> respuestas;

    @ManyToOne
    private Comment padre;

    @OneToMany(mappedBy = "comentario")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<CommentVote> votos;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Topic getTema() {
        return tema;
    }

    public void setTema(Topic tema) {
        this.tema = tema;
    }

    public List<Comment> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Comment> respuestas) {
        this.respuestas = respuestas;
    }

    public Comment getPadre() {
        return padre;
    }

    public void setPadre(Comment padre) {
        this.padre = padre;
    }

    public List<CommentVote> getVotos() {
        return votos;
    }

    public void setVotos(List<CommentVote> votos) {
        this.votos = votos;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
    
}
