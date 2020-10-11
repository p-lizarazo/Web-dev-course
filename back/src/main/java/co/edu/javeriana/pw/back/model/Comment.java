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

@Entity
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private Date fechaPublicacion;
    
    @ManyToOne
    private Topic tema;

    @OneToMany(mappedBy = "padre")
    private List<Comment> respuestas;

    @ManyToOne
    private Comment padre;

    @OneToMany(mappedBy = "comentario")
    private List<CommentVote> votos;
    //private User owner
}
