package co.edu.javeriana.pw.back.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    private List<Comment> comentarios;
    
    @OneToMany(mappedBy = "tema")
    private List<TopicVote> votos;

    //private User owner;
}
