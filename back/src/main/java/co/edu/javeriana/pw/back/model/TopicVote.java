package co.edu.javeriana.pw.back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TopicVote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Boolean meGusta;

    @ManyToOne
    private Topic tema;
    //private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Topic getTema() {
        return tema;
    }

    public void setTema(Topic tema) {
        this.tema = tema;
    }
}
