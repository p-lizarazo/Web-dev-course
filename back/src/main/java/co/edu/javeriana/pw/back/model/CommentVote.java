package co.edu.javeriana.pw.back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CommentVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Comment comentario;

    private Boolean meGusta;
    //private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment getComentario() {
        return comentario;
    }

    public void setComentario(Comment comentario) {
        this.comentario = comentario;
    }

    public Boolean getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    
}
